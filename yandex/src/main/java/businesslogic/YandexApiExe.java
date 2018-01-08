package businesslogic;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class YandexApiExe {

    private static volatile YandexApiExe instance;
    private static Properties properties;
    private InputStream input = null;
    private String yandexApiKey;
    //private static final Logger log = Logger.getLogger(YandexApiExe.class.getName());
    private static final Logger log  = LoggerFactory.getLogger(YandexApiExe.class);

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
        HttpResponse httpResponse = null;
        //HttpGet get = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180101T103022Z.a3ec12ad40f2085c.ee440ea7adc10858247b5143e69e4185fdb9eb65&text=town&lang=en-ru");
        HttpGet get = new HttpGet(uriCreator(wordForTranslate));
        HttpClient httpClient = HttpClients.createDefault();

        httpResponse = httpClient.execute(get);
        HttpEntity entity = httpResponse.getEntity();

        String json = EntityUtils.toString(entity, "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);

        JSONObject obj = (JSONObject) resultObject;
        String raw = obj.get("text").toString();

        //remove first 2 and last 2 symbols
        result = raw.substring(2, raw.length() - 2);

        return result;
    }

    private String uriCreator(String wordForTranslate) {

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
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("https://translate.yandex.net/api/v1.5/tr.json/translate");
         stringBuilder.append(String.format("?key=%s",yandexApiKey));
        stringBuilder.append(String.format("&text=%s", wordForTranslate));
        stringBuilder.append("&lang=en-ru");

        return stringBuilder.toString();
    }
}
