import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.net.URI;

/**
 * Created by user on 1/2/2018.
 */
public class TestHttpResponse {
/*    @Test
    void HttpResponseTest() {
        URI remoteArtifact = new URI();

        HttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest httpRequest = RequestBuilder.head().setUri(remoteArtifact).build();

        HttpResponse httpResponse = httpClient.execute(httpRequest);


    }*/

   /* @Test
    public void GetTranslateWordFromYandexViaProxy() {
        try {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope("10.5.45.250", 3128),
                    new UsernamePasswordCredentials("corp/korotkov_a_a", ""));
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider).build();

            HttpResponse httpResponse = null;

            HttpHost proxy = new HttpHost("10.5.45.250", 3128);
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();

            HttpGet get = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180101T103022Z.a3ec12ad40f2085c.ee440ea7adc10858247b5143e69e4185fdb9eb65&text=town&lang=en-ru");
            get.setConfig(config);

            httpResponse = httpClient.execute(get);
            HttpEntity entity = httpResponse.getEntity();

            String json = EntityUtils.toString(entity, "UTF-8");

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);

            JSONObject obj = (JSONObject) resultObject;
            String raw = obj.get("text").toString();

            String parsedString = raw.substring(2, raw.length() - 2);

            Assert.assertEquals(parsedString, "город");

        } catch (Exception e) {
            e.getStackTrace();
        }
    }*/

    /*@Test
    public void GetTranslateWordFromYandexViaProxyUseSSl() throws Exception{
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("10.5.45.250", 3128),
                new UsernamePasswordCredentials("corp/korotkov_a_a", ""));

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial((chain, authType) -> true).build();

        SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(sslContext, new String[]
                        {"SSLv2Hello", "SSLv3", "TLSv1","TLSv1.1", "TLSv1.2" }, null,
                        NoopHostnameVerifier.INSTANCE);
        //-------------------------------

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).setSSLSocketFactory(sslSocketFactory).build();

        HttpResponse httpResponse = null;

        HttpHost proxy = new HttpHost("10.5.45.250", 3128);
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        HttpGet get = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180101T103022Z.a3ec12ad40f2085c.ee440ea7adc10858247b5143e69e4185fdb9eb65&text=town&lang=en-ru");
        get.setConfig(config);

        httpResponse = httpClient.execute(get);
        HttpEntity entity = httpResponse.getEntity();

        String json = EntityUtils.toString(entity, "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);

        JSONObject obj = (JSONObject) resultObject;
        String raw = obj.get("text").toString();

        String parsedString = raw.substring(2, raw.length() - 2);

        Assert.assertEquals(parsedString, "город");
    }*/
}
