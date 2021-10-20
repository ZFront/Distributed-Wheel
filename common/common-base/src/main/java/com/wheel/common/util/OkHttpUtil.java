package com.wheel.common.util;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class OkHttpUtil {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final int CONNECT_TIMEOUT = 10;
    private static final int WRITE_TIMEOUT = 20;
    private static final int READ_TIMEOUT = 20;

    private static OkHttpClient httpClient = getOkHttpsClient();

    public static String getSync(String url, Map<String, String> header, Map<String, String> param) throws Exception {
        if (param != null) {
            int i = 0, len = param.size();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                if (i == 0) {
                    url += "?";
                }
                url += entry.getKey() + "=" + entry.getValue();
                if (i < len - 1) {
                    url += "&";
                }
                i++;
            }
        }

        Request.Builder builder = new Request.Builder();
        addHeader(builder, header);
        Request request = builder.url(getUrl(url)).get().build();
        Response response = httpClient.newCall(request).execute();
        return getRespBodyAndClose(response);
    }

    public static String postFromSync(String url, Map<String, String> param) throws Exception {
        return postFromSync(url, null, param);
    }

    /**
     * 提交Form表单
     *
     * @param url
     * @param param
     * @return
     */
    public static String postFromSync(String url, Map<String, String> header, Map<String, String> param) throws Exception {
        FormBody.Builder bodBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            bodBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = bodBuilder.build();

        Request.Builder reqBuilder = new Request.Builder();
        addHeader(reqBuilder, header);

        final Request request = reqBuilder.url(getUrl(url)).post(body).build();
        Response response = httpClient.newCall(request).execute();
        return getRespBodyAndClose(response);
    }

    /**
     * post同步请求，提交Json数据
     *
     * @param url
     * @param json
     * @return
     */
    public static String postJsonSync(String url, String json) throws Exception {
        final RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
        final Request request = new Request.Builder()
                .url(getUrl(url))
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        return getRespBodyAndClose(response);
    }

    /**
     * post同步请求，提交Json数据
     *
     * @param url
     * @param json
     * @return
     */
    public static String postJsonSync(String url, Map<String, String> header, String json) throws Exception {
        final RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
        Request.Builder builder = new Request.Builder();
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        final Request request = builder.url(getUrl(url))
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        return getRespBodyAndClose(response);
    }

    /**
     * post异步请求，提交Json数据
     */
    public static <T> void postJsonAsync(String url, String json, Consumer<String> onComplete, Consumer<IOException> onFail) {
        final RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
        final Request request = new Request.Builder()
                .url(getUrl(url))
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onFail != null) {
                    onFail.accept(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (onComplete != null) {
                    onComplete.accept(response.body().string());
                }
            }
        });
    }

    public static OkHttpClient getOkHttpsClient() {
        try {
            final X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    if (chain == null) {
                        throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
                    }
                    if (!(chain.length > 0)) {
                        throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
                    }
                    try {
                        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                        tmf.init((KeyStore) null);
                        for (TrustManager trustManager : tmf.getTrustManagers()) {
                            ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                        }
                    } catch (Exception e) {
                        throw new CertificateException(e);
                    }
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.proxy(Proxy.NO_PROXY); //不使用代理，避免被第三方使用代理抓包
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)//不允许重试
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else {
            return "http://" + url;
        }
    }

    private static void addHeader(Request.Builder builder, Map<String, String> header) {
        if (header == null || header.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
    }

    private static String getRespBodyAndClose(Response response) throws Exception {
        try {
            return response.body().string();
        } finally {
            response.close();
        }
    }
}
