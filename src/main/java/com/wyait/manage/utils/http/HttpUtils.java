package com.wyait.manage.utils.http;

import com.wyait.manage.utils.http.model.ClientResponse;
import com.wyait.manage.utils.http.model.FaceBean;
import com.wyait.manage.utils.http.model.VoiceBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @User: Liangsongzhu.
 * @Date: 2017/3/28.
 * @Time: 15:12.
 * @Version: 1.0.0
 * @Description: HTTP请求工具类
 */
@Slf4j
public class HttpUtils {
    HttpClientConnectionManager cm = null;

    /**
     * httpclient4.5.2版
     * 忽略服务器证书，采用信任机制
     *
     * @return
     */
    private static HttpClientConnectionManager buildManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Registry registry = RegistryBuilder
                    .create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf).build();
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
            poolingHttpClientConnectionManager.setMaxTotal(400);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(200);

            return poolingHttpClientConnectionManager;
        } catch (Exception e) {
            throw e;
        }
    }

    private static HttpUtils instance = null;

    public static HttpUtils getInstance() {
        if (instance == null) {
            instance = new HttpUtils();
        }
        return instance;
    }

    public CloseableHttpClient getHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return HttpClients.custom().setConnectionManager(buildManager()).build();
    }

    /**
     * HTTP GET
     *
     * @param url     请求地址
     * @param querys  参数
     * @param headers 请求头
     * @return
     * @throws Exception
     */
    public static ClientResponse httpGet(String url,
                                         Map<String, String> querys,
                                         Map<String, String> headers) throws Exception {
        return httpGet(url, querys, headers, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT, Constants.DEFAULT_REQUEST_TIMEOUT);
    }

    /**
     * HTTP GET
     *
     * @param url            请求地址
     * @param querys         参数
     * @param headers        请求头
     * @param connectTimeout 连接主机超时时间
     * @param sokectTimeout  读取数据超时时间
     * @param requestTimeout 请求连接池超时时间
     * @return
     * @throws Exception
     */
    public static ClientResponse httpGet(String url,
                                         Map<String, String> querys,
                                         Map<String, String> headers,
                                         Integer connectTimeout,
                                         Integer sokectTimeout,
                                         Integer requestTimeout) throws Exception {
        log.info("[httpGet start] URL:{}", url);
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = HttpUtils.getInstance().getHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(connectTimeout).
                setSocketTimeout(sokectTimeout).
                setConnectionRequestTimeout(requestTimeout).
                build();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet get = null;
            if (querys != null && !querys.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                Set<Map.Entry<String, String>> entrySet = querys.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                log.info("[httpGet start] URL:{}", (url + "?" + EntityUtils.toString(entity)));
                get = new HttpGet(url + "?" + EntityUtils.toString(entity));
            } else if (url.indexOf("&") > -1 && url.indexOf("?") > -1) {
                get = new HttpGet(url);
            } else {
                throw new RuntimeException("请求参数不能为空");
            }

            get.setConfig(requestConfig);
            if (headers != null) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    get.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
                }
            }
            httpResponse = httpClient.execute(get);
            ClientResponse res = convert(httpResponse, "UTF-8");
            log.info("[httpGet finish] URL:{} useTimes:{}", url, (System.currentTimeMillis() - startTime));
            return res;
        } catch (Exception e) {
            log.error("[httpGet exception] URL:{} useTimes:{}", url, (System.currentTimeMillis() - startTime));
            throw e;
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    private static HttpContext localContext = new BasicHttpContext();
    private static HttpClientContext context = HttpClientContext.adapt(localContext);

    private static String POST_JSON = "POST_JSON"; //json 字符串
    private static String POST_FORM = "POST_FORM"; //普通表单
    private static String POST_MULI_FORM = "POST_MULI_FORM"; //带字节流表单

    public static ClientResponse httpPostJson(String url,
                                              String jsonBodys,
                                              Map<String, String> headers) throws Exception {
        return httpPost(url, jsonBodys, null, null, headers, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT, Constants.DEFAULT_REQUEST_TIMEOUT, POST_JSON, false);
    }

    public static ClientResponse httpPostForm(String url,
                                              Map<String, String> bodys,
                                              Map<String, String> headers) throws Exception {
        return httpPost(url, null, bodys, null, headers, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT, Constants.DEFAULT_REQUEST_TIMEOUT, POST_FORM, false);
    }

    public static ClientResponse httpPostHasCookie(String url,
                                                   Map<String, String> bodys,
                                                   Map<String, String> headers) throws Exception {
        return httpPost(url, null, bodys, null, headers, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT, Constants.DEFAULT_REQUEST_TIMEOUT, POST_FORM, true);
    }

    public static ClientResponse httpPost(String url,
                                          String jsonParams,
                                          Map<String, String> maps,
                                          Map<String, Object> muliMap,
                                          Map<String, String> headers,
                                          Integer connectTimeout,
                                          Integer sokectTimeout,
                                          Integer requestTimeout,
                                          String postType,
                                          Boolean hasContext) throws Exception {
        log.info("[httpPost start] URL:{}", url);
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = HttpUtils.getInstance().getHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(connectTimeout).
                setSocketTimeout(sokectTimeout).
                setConnectionRequestTimeout(requestTimeout).
                build();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);

            if (postType.equals(POST_JSON)) {
                StringEntity entity = new StringEntity(jsonParams, org.apache.http.entity.ContentType.create("application/json", "UTF-8"));
                post.setEntity(entity);
            }

            if (postType.equals(POST_FORM)) {
                if (maps != null && !maps.isEmpty()) {
                    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                    Set<Map.Entry<String, String>> entrySet = maps.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                    entity.setContentType(ContentType.CONTENT_TYPE_FORM);
                    post.setEntity(entity);
                }
            }

            if (postType.equals(POST_MULI_FORM)) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                for (String key : muliMap.keySet()) {
                    if (muliMap.get(key) instanceof String) {
                        builder.addTextBody(key, String.valueOf(muliMap.get(key)), org.apache.http.entity.ContentType.TEXT_PLAIN);
                    } else if (muliMap.get(key) instanceof byte[]) {
                        builder.addBinaryBody(key, (byte[]) muliMap.get(key), org.apache.http.entity.ContentType.DEFAULT_BINARY, new StringBuffer().append(key).append(System.currentTimeMillis()).toString());
                    }
                }
                post.setEntity(builder.build());
            }

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }

            if (hasContext) {
                httpResponse = httpClient.execute(post, context);
            } else {
                httpResponse = httpClient.execute(post);
            }
            ClientResponse res = convert(httpResponse, "UTF-8");
            log.info("[httpPost finish] URL:{} useTimes:{}", url, (System.currentTimeMillis() - startTime));
            return res;
        } catch (Exception e) {
            log.info("[httpPost Exception] URL:{} useTimes:{}", url, (System.currentTimeMillis() - startTime));
            throw e;
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * httpMultiPost
     *
     * @param url     请求地址
     * @param bodys   参数
     * @param headers 请求头
     * @return
     * @throws Exception
     */
    public static ClientResponse httpMultiPost(String url,
                                               Map<String, Object> bodys,
                                               Map<String, String> headers) throws Exception {
        return httpPost(url, null, null, bodys, headers, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT, Constants.DEFAULT_REQUEST_TIMEOUT, POST_MULI_FORM, false);
    }

    /**
     * httpMultiPost
     *
     * @param url            请求地址
     * @param bodys          参数
     * @param headers        请求头
     * @param connectTimeout 连接主机超时时间
     * @param sokectTimeout  读取数据超时时间
     * @param requestTimeout 请求连接池超时时间
     * @return
     * @throws Exception
     */
    public static ClientResponse httpMultiPost(String url,
                                               Map<String, Object> bodys,
                                               Map<String, String> headers,
                                               Integer connectTimeout,
                                               Integer sokectTimeout,
                                               Integer requestTimeout) throws Exception {
        return httpPost(url, null, null, bodys, headers, connectTimeout, sokectTimeout, requestTimeout, POST_MULI_FORM, false);
    }

    /**
     * httpMultiPost
     *
     * @param url            请求地址
     * @param params         参数
     * @param headers        请求头
     * @param voiceParams    声纹
     * @param faceParams     脸型
     * @param connectTimeout 连接主机超时时间
     * @param sokectTimeout  读取数据超时时间
     * @param requestTimeout 请求连接池超时时间
     * @return
     * @throws Exception
     */
    public static ClientResponse httpMultiPost(String url,
                                               Map<String, Object> params,
                                               Map<String, String> headers,
                                               List<VoiceBean> voiceParams,
                                               List<FaceBean> faceParams,
                                               Integer connectTimeout,
                                               Integer sokectTimeout,
                                               Integer requestTimeout) throws Exception {
        log.info("[httpMultiPost start] URL:{}", url);
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = HttpUtils.getInstance().getHttpClient();
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(connectTimeout).
                setSocketTimeout(sokectTimeout).
                setConnectionRequestTimeout(requestTimeout).
                build();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            if (null != headers) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    post.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
                }
            }
            post.setEntity(generateReqEntity(params, voiceParams, faceParams));
            httpResponse = httpClient.execute(post);
            ClientResponse res = convert(httpResponse, "UTF-8");
            log.info("[httpMultiPost finish] URL:{} useTimes:{}", url, (System.currentTimeMillis() - startTime));
            return res;
        } catch (Exception e) {
            log.error("[httpMultiPost exception] URL:{} useTimes:{}", url, (System.currentTimeMillis() - startTime));
            throw e;
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * urlConnecPost
     *
     * @param uri   请求地址
     * @param datas 参数
     * @return
     * @throws Exception
     */
    public static String urlConnecPost(String uri, String datas) throws Exception {
        return urlConnecPost(uri, datas, null, ContentType.CONTENT_TYPE_TEXT, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT);
    }

    /**
     * urlConnecPostDoc
     *
     * @param uri
     * @param soapDatas
     * @param soapAction
     * @return
     * @throws Exception
     */
    public static Document urlConnecPostDoc(String uri, String soapDatas, String soapAction) throws Exception {
        return urlConnecPostDoc(uri, soapDatas, soapAction, ContentType.CONTENT_TYPE_TEXT, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT);
    }

    /**
     * @param uri
     * @param soapDatas
     * @param soapAction
     * @param contentType
     * @return
     * @throws Exception
     */
    public static Document urlConnecPostDoc(String uri, String soapDatas, String soapAction, String contentType) throws Exception {
        return urlConnecPostDoc(uri, soapDatas, soapAction, contentType, Constants.DEFAULT_TIMEOUT, Constants.DEFAULT_READ_TIMEOUT);
    }

    /**
     * httpURLConnecPost
     *
     * @param uri            请求地址
     * @param soapDatas      参数
     * @param soapAction     参数
     * @param contentType    提交格式
     * @param connectTimeout 连接主机超时时间
     * @param readTimeout    读取数据超时时间
     * @return
     * @throws IOException
     */
    public static String urlConnecPost(String uri, String soapDatas, String soapAction, String contentType, Integer connectTimeout, Integer readTimeout) throws Exception {
        log.info("[urlConnecPost start] URL:{}", uri);
        long startTime = System.currentTimeMillis();
        try {
            InputStream inputStream = urlConnection(uri, soapDatas, soapAction, contentType, connectTimeout, readTimeout);
            log.info("]urlConnecPost finish] URL:{} useTimes:{}", uri, (System.currentTimeMillis() - startTime));
            return convert2String(inputStream);
        } catch (IOException e) {
            log.error("[urlConnecPost exception] URL:{} useTimes:{}", uri, (System.currentTimeMillis() - startTime));
            throw e;
        }
    }


    public static Document urlConnecPostDoc(String uri, String soapDatas, String soapAction, String contentType, Integer connectTimeout, Integer readTimeout) throws Exception {
        log.info("[urlConnecPostDoc start] URL:{}", uri);
        long startTime = System.currentTimeMillis();
        try {
            InputStream inputStream = urlConnection(uri, soapDatas, soapAction, contentType, connectTimeout, readTimeout);
            log.info("[urlConnecPostDoc finish] URL:{} useTimes:{}", uri, (System.currentTimeMillis() - startTime));
            return convert2Doc(inputStream);
        } catch (IOException e) {
            log.error("[urlConnecPostDoc exception] URL:{} useTimes:{}", uri, (System.currentTimeMillis() - startTime));
            throw e;
        }
    }

    private static String convert2String(InputStream inputStream) throws Exception {
        StringBuffer sb = new StringBuffer();
        // 获取输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Constants.EN_CODING));
        char[] buf = new char[1024];
        int length = 0;
        while ((length = reader.read(buf)) > 0) {
            sb.append(buf, 0, length);
        }
        reader.close();
        return sb.toString();
    }

    private static Document convert2Doc(InputStream inputStream) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(inputStream);
        String docString = doc.getRootElement().asXML();
        String str = docString;
        if (str.indexOf("&lt;") > 0 && str.indexOf("&gt;") > 0) {
            // 过滤document头
            if (str.indexOf("&lt;?") > 0 && str.indexOf("?&gt;") > 0) {
                int start = str.indexOf("&lt;?");
                int end = str.indexOf("?&gt;");
                str = str.substring(0, start) + str.substring(end + 5);
            }
            str = str.replace("&lt;", "<").replace("&gt;", ">");
            doc = DocumentHelper.parseText(str);
        }
        inputStream.close();
        return doc;
    }

    /**
     * @param uri
     * @param soapDatas
     * @param soapAction
     * @param contentType
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    private static InputStream urlConnection(String uri, String soapDatas, String soapAction, String contentType, Integer connectTimeout, Integer readTimeout) throws Exception {
        URL url = null;
        HttpURLConnection httpConn = null;
        try {
            url = new URL(uri);
            httpConn = (HttpURLConnection) url.openConnection();

            if ("https".equalsIgnoreCase(url.getProtocol())) {
                if (httpConn instanceof HttpsURLConnection) {
                    SslContextUtils sslContextUtils = new SslContextUtils();
                    sslContextUtils.initHttpsConnect((HttpsURLConnection) httpConn);
                }
            }

            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            if (StringUtils.isNotBlank(contentType)) {
                httpConn.setRequestProperty("Content-type", contentType);
            }
            httpConn.setRequestProperty("Content-Length", Integer.toString(soapDatas.length()));
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
            if (soapAction != null && !"".equals(soapAction)) {
                httpConn.setRequestProperty("SOAPAction", soapAction);
            }
            httpConn.setConnectTimeout(connectTimeout);
            httpConn.setReadTimeout(readTimeout);

            // 发送请求
            httpConn.getOutputStream().write(soapDatas.getBytes(Constants.EN_CODING));
            httpConn.getOutputStream().flush();
            httpConn.getOutputStream().close();
        } catch (Exception e) {
            throw e;
        } finally {
            httpConn.disconnect();
        }
        return httpConn.getInputStream();
    }

    /**
     * 构建多维认证请求参数
     *
     * @param params      参数
     * @param voiceParams 声纹
     * @param faceParams  脸形
     * @return
     * @throws IOException
     */
    private static HttpEntity generateReqEntity(Map<String, Object> params, List<VoiceBean> voiceParams, List<FaceBean> faceParams) throws IOException {
        String BOUNDARY = "Boundary-no-uuid";
        final byte[] start = ("--" + BOUNDARY + "\r\n").getBytes();
        final byte[] end = ("--" + BOUNDARY + "--" + "\r\n").getBytes();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (params != null) {
                //普通参数
                for (String key : params.keySet()) {
                    byteArrayOutputStream.write(start);
                    byteArrayOutputStream.write(String.format(Locale.getDefault(), "Content-Disposition:form/data;name=\"%s\"\r\n\r\n", key).getBytes(Constants.EN_CODING));
                    byteArrayOutputStream.write(String.valueOf(params.get(key)).getBytes(Constants.EN_CODING));
                    byteArrayOutputStream.write("\r\n".getBytes());
                }
            }
            //语音参数
            if (voiceParams != null) {
                for (VoiceBean voiceBean : voiceParams) {
                    byteArrayOutputStream.write(start);
                    byteArrayOutputStream.write(String.format(Locale.getDefault(), "Content-Disposition:form/data;name=\"%s\";filename=\"%s\"\r\n", voiceBean.getVoiceKeyName(), voiceBean.getFilename()).getBytes(Constants.EN_CODING));
                    byteArrayOutputStream.write("Content-Type:application/octet-stream\r\n\r\n".getBytes(Constants.EN_CODING));
                    byteArrayOutputStream.write(voiceBean.getVoiceData(), 0, voiceBean.getVoiceData().length);
                    byteArrayOutputStream.write("\r\n".getBytes());
                }
                for (VoiceBean voiceBean : voiceParams) {
                    if (StringUtils.isNotBlank(voiceBean.getVoiceTxt())) {
                        byteArrayOutputStream.write(start);
                        byteArrayOutputStream.write(String.format(Locale.getDefault(), "Content-Disposition:form/data;name=\"%s\"\r\n\r\n", voiceBean.getVoiceTxtKeyName()).getBytes(Constants.EN_CODING));
                        byteArrayOutputStream.write(voiceBean.getVoiceTxt().getBytes(Constants.EN_CODING));
                        byteArrayOutputStream.write("\r\n".getBytes());
                    }
                }
            }
            //人脸参数
            if (faceParams != null) {
                for (FaceBean faceBean : faceParams) {
                    byteArrayOutputStream.write(start);
                    byteArrayOutputStream.write(String.format(Locale.getDefault(), "Content-Disposition:form/data;name=\"%s\";filename=\"%s\"\r\n", faceBean.getFaceKeyName(), faceBean.getFilename()).getBytes(Constants.EN_CODING));
                    byteArrayOutputStream.write("Content-Type:application/octet-stream\r\n\r\n".getBytes(Constants.EN_CODING));
                    /*int len = System.in.read(faceBean.getImg());*/
                    byteArrayOutputStream.write(faceBean.getImg(), 0, faceBean.getImg().length);
                    byteArrayOutputStream.write("\r\n".getBytes());
                }
            }
            byteArrayOutputStream.write(end);
            ByteArrayEntity bae = new ByteArrayEntity(byteArrayOutputStream.toByteArray());
            bae.setContentType("multipart/form-data;boundary=" + BOUNDARY);
            bae.setContentEncoding(Constants.EN_CODING);
            byteArrayOutputStream.close();
            return bae;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 构建FormEntity
     *
     * @param formParam
     * @return
     * @throws UnsupportedEncodingException
     */
    private static UrlEncodedFormEntity buildFormEntity(Map<String, String> formParam)
            throws UnsupportedEncodingException {
        if (formParam != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (String key : formParam.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, formParam.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, Constants.EN_CODING);
            return formEntity;
        }
        return null;
    }

    /**
     * 组装请求URL
     *
     * @param url    请求地址
     * @param querys 请求参数
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String initUrl(String url, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder(url);
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append(Constants.SPE3);
                }
                if (StringUtils.isNotBlank(query.getKey()) && StringUtils.isNotBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }

                if (StringUtils.isNotBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (StringUtils.isNotBlank(query.getValue())) {
                        sbQuery.append(Constants.SPE4);
                        sbQuery.append(URLEncoder.encode(query.getValue(), Constants.EN_CODING));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append(Constants.SPE5).append(sbQuery);
            }
        }
        return sbUrl.toString();
    }

    /**
     * 转化返回报文
     *
     * @param response 返回RESPONSE
     * @return
     * @throws IOException
     */
    private static ClientResponse convert(HttpResponse response, String charset) throws IOException {
        ClientResponse res = new ClientResponse();
        if (null != response) {
            res.setStatusCode(response.getStatusLine().getStatusCode());
            for (Header header : response.getAllHeaders()) {
                if ("UTF-8".equals(charset)) {
                    res.setHeader(header.getName(), header.getValue());
                } else {
                    res.setHeader(header.getName(), MessageDigestUtil.iso88591ToUtf8(header.getValue()));
                }
            }
            res.setContentType(res.getHeader("Content-Type"));
            res.setRequestId(res.getHeader("X-Ca-Request-Id"));
            res.setErrorMessage(res.getHeader("X-Ca-Error-Message"));
            if (null != response.getEntity()) {
                res.setBody(EntityUtils.toString(response.getEntity(), charset));
            }
        } else {
            //服务器无回应
            res.setStatusCode(500);
            res.setErrorMessage("No Response");
        }

        return res;
    }
}
