package businesslogic.yandex;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class RequestCreator {

    RequestConfig getRequestConfig() throws Exception{

        String host;
        String port;

        host = CredentialSetter.host;
        port = CredentialSetter.port;

        HttpHost proxy = new HttpHost(host, Integer.parseInt(port));
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        return config;
    }

    public HttpEntity getEntity(HttpGet get, CredentialsProvider credentialsProvider)
        throws Exception {
        HttpEntity entity;
        HttpResponse httpResponse;
        CloseableHttpClient httpClient;
        SSLConnectionSocketFactory sslSocketFactory;

        sslSocketFactory = this.getSslSocketFactory();

        httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        httpResponse = httpClient.execute(get);
        entity = httpResponse.getEntity();

        return entity;
    }

    private SSLConnectionSocketFactory getSslSocketFactory() throws Exception {
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(
                        new TrustStrategy() {
                            @Override
                            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                return true;
                            }
                        })
                .build();

        SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(sslContext, new String[]
                        {"SSLv2Hello", "SSLv3", "TLSv1","TLSv1.1", "TLSv1.2" }, null,
                        NoopHostnameVerifier.INSTANCE);

        return sslSocketFactory;
    }
}
