package com.wyait.manage.utils.http;

import javax.net.ssl.*;

/**
 * @User: Liangsongzhu.
 * @Date: 2016/11/17.
 * @Time: 11:27.
 * @Version: 1.0.0
 * @Description: 忽略证书
 */
public class SslContextUtils {
    private TrustManager trustAllManager;
    SSLContext sslcontext;
    HostnameVerifier allHostsValid;

    public SslContextUtils() {
        initContext();
    }

    private void initContext() {
        trustAllManager = new X509TrustManager() {

            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

        };
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { trustAllManager }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    public void initHttpsConnect(HttpsURLConnection httpsConn){
        httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());
        httpsConn.setHostnameVerifier(allHostsValid);
    }
}
