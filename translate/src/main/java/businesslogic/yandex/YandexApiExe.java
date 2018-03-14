package businesslogic.yandex;

import businesslogic.Translator;
import dbconnector.DbConnector;

import java.io.BufferedReader;
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
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class YandexApiExe implements Translator {
 
    private static volatile YandexApiExe instance;
    private static Properties properties;
    private String yandexApiKey = "empty";
    private String pathToProperties = "yandexapi.properties";

    private final static Logger LOG = LoggerFactory.getLogger(YandexApiExe.class);

        private YandexApiExe() throws Exception {
        propertyLoader();
    }

    private void propertyLoader() throws Exception{
            properties = new Properties();

            Path path = Paths.get(pathToProperties);
            BufferedReader input = Files.newBufferedReader(path, Charset.forName("UTF-8"));

            properties.load(input);
            input.close();
            yandexApiKey = properties.getProperty("yandexKey");
    }

    public static YandexApiExe getInstance() throws Exception{
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

        result = connector.doGetTranslatedWordFromDb(wordForTranslate, resolvedLn);

        if(result.equals("")) {
            result = doTranslate(wordForTranslate);
            connector.doInsertToDbResult(wordForTranslate, resolvedLn, result);
        }
        connector.closeConnection(connector.getConnection());

        return result;
    }

    public String doTranslate(String wordForTranslate) throws Exception{

        String result;
        HttpResponse httpResponse = null;
        //HttpGet get = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=!!KEY!!&text=town&lang=en-ru");
        HttpGet get = new HttpGet(uriCreator(wordForTranslate));
        HttpClient httpClient = HttpClients.createDefault();
        httpResponse = httpClient.execute(get);
        HttpEntity entity = httpResponse.getEntity();

        result = doJsonParse(entity);

        if(result.equals("")) {
            LOG.error(String.format("Req = %s.Result in null.", wordForTranslate));
        } else {
            LOG.info(String.format("Req = %s. Result in not null.", wordForTranslate));
        }
        return result;
    }

    private String doJsonParse(HttpEntity entity) throws ParseException, IOException {
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
