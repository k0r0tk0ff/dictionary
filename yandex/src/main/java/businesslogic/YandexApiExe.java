package businesslogic;

import dbconnector.DbConnector;
import java.io.IOException;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

//TODO: в данном классе смешаны зоны ответственности
//по сути за внесение в БД не отвечает модуль db
//пакет с бизнес логикой отвечает за работу со сторонним API яндекса. хотя ничего не должен о нем знать
//попробуй пересмотреть деление
//ну и название класса так же не выражает суть, то есть по сути ты акцентируешь внимание на том, 
//что у тебя происходит работа через API Яндекса, хотя со временм можкшь использовать и свое
//попробуй реализовать через выделение интерфейса для работы с API переводчика

public class YandexApiExe {

    private static volatile YandexApiExe instance;
    private static Properties properties;
    private InputStream input = null;
    private String yandexApiKey;

    private final static Logger LOG = LoggerFactory.getLogger(YandexApiExe.class);

    private YandexApiExe() {

        properties = new Properties();

        input = this.getClass().getClassLoader().getResourceAsStream("yandexapi.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        yandexApiKey = properties.getProperty("yandexKey");
    }

    public static YandexApiExe getInstance() {
        YandexApiExe localInstance = instance;
        if (localInstance == null) {
            synchronized (YandexApiExe.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new YandexApiExe();
                }
            }
        }
        return localInstance;
    }

    public String doGetTranslatedWord(String wordForTranslate) throws Exception {
        String result = "";
        String resolvedLn;
        DbConnector connector = DbConnector.getInstance();
        connector.initializeConnection();

        resolvedLn = doDetectLanguage(wordForTranslate);

        result = doGetTranslatedWordFromDb(wordForTranslate, resolvedLn, connector);

        if(result.equals("")) {
            result = doYandexApiWork(wordForTranslate);
            doInsertToDbResult(wordForTranslate, resolvedLn, connector, result);
        }
        connector.closeConnection(connector.getConnection());

        return result;
    }

    private void doInsertToDbResult(
            String wordForTranslate,
            String resolvedLn,
            DbConnector connector,
            String transWord) throws SQLException {
        String reverse;

        reverse = (resolvedLn.equals("ru")) ? "en" : "ru";

        String sql = String.format("INSERT INTO DICTIONARY (%s, %s) VALUES (?, ?);",resolvedLn, reverse);
        PreparedStatement pr = connector.getConnection().prepareStatement(sql);
        pr.setString(1, wordForTranslate);
        pr.setString(2, transWord);

        pr.executeUpdate();
        pr.close();

    }

    private String doGetTranslatedWordFromDb (String wordForTranslate, String resolvedLn, DbConnector connector)
            throws Exception {
        String result = "";

        String sql = String.format("SELECT * FROM DICTIONARY WHERE %s = '%s';", resolvedLn, wordForTranslate);

        PreparedStatement pr = connector.getConnection().prepareStatement(sql);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()) {
                String english = resultSet.getString("en");
                String russian = resultSet.getString("ru");

                result = String.format("%s = %s", english, russian);
            }

        pr.close();

        //System.out.println("result from DB = " + result);
        return result;
    }

    private String doYandexApiWork(String wordForTranslate) throws Exception{

        String result;
        HttpResponse httpResponse = null;
        //HttpGet get = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180101T103022Z.a3ec12ad40f2085c.ee440ea7adc10858247b5143e69e4185fdb9eb65&text=town&lang=en-ru");
        HttpGet get = new HttpGet(uriCreator(wordForTranslate));
        HttpClient httpClient = HttpClients.createDefault();
        httpResponse = httpClient.execute(get);
        HttpEntity entity = httpResponse.getEntity();

        result = doJsonParse(entity);

/*        if(result.equals("")) {
            LOG.error(String.format("Req = %s.Result in null.", wordForTranslate));
        } else {
            LOG.info(String.format("Req = %s. Result in not null.", wordForTranslate));
        }*/
        return result;
    }

    private String doJsonParse(HttpEntity entity) throws Exception{
        String parsedString;

        String json = EntityUtils.toString(entity, "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);

        JSONObject obj = (JSONObject) resultObject;
        String raw = obj.get("text").toString();

        parsedString = raw.substring(2, raw.length() - 2);
        return parsedString;
    }

    private String uriCreator(String wordForTranslate) throws Exception{

/*
 See
 https://tech.yandex.ru/translate/doc/dg/reference/translate-docpage/

 https://translate.yandex.net/api/v1.5/tr.json/translate
 ? [key=<API-ключ>]
 & [text=<переводимый текст>]
 & [lang=<направление перевода>]
 & [format=<формат текста>]
 & [options=<опции перевода>]
 & [callback=<имя callback-функции>]
*/
        String en2ru;
        String ru2en;
        String detectedLang;
        String resolvedLn;

        en2ru = "&lang=en-ru";
        ru2en = "&lang=ru-en";

        resolvedLn = doDetectLanguage(wordForTranslate);

        if(resolvedLn.equals("ru")) {
            detectedLang = ru2en;
        } else {
            detectedLang = en2ru;
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("https://translate.yandex.net/api/v1.5/tr.json/translate");
        stringBuilder.append(String.format("?key=%s",yandexApiKey));
        stringBuilder.append(String.format("&text=%s", wordForTranslate));
        stringBuilder.append(detectedLang);

        return stringBuilder.toString();
    }

    String doDetectLanguage(String wordForDetectLn) throws IOException {

        Set<String> languages = new HashSet<String>();
        languages.add("ru");
        languages.add("en");

        LanguageDetector detector = new OptimaizeLangDetector().loadModels(languages);
        LanguageResult result = detector.detect(wordForDetectLn);

        return result.getLanguage();
    }
}
