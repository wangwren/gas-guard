package com.gas.utils;


import cn.hutool.core.util.StrUtil;
import com.gas.enums.ErrorCodeEnum;
import com.gas.exception.CommonException;
import okhttp3.*;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class OkHttpUtils {
    private static final int DEFAULT_CONNECTION_TIMEOUT = 3;
    private static final int DEFAULT_READ_TIMEOUT = 10;
    /**
     * 最大连接数
     */
    private static final int MAX_REQUESTS = 200;
    /**
     * 每个域名最大连接数
     */
    private static final int MAX_REQUESTS_PER_HOST = 50;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MULTIPART = MediaType.parse(" multipart/form-data");
    private static final MediaType TEXT = MediaType.parse("text/plain");
    private static final OkHttpClient client;
    private static final SecureRandom random;

    private OkHttpUtils() {
    }

    public static Response postWithHeaders(String url, Map<String, String> headers, String
            paramJson) throws IOException {
        RequestBody body = RequestBody.create(JSON, paramJson);
        Request.Builder builder = (new Request.Builder()).url(url);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder = builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.post(body).build();

        return client.newCall(request).execute();
    }

    public static Response get(String url) throws IOException {
        return get(client, url);
    }

    public static Response post(String url, Map<String, String> params) throws IOException {
        return postParam(client, url, params);
    }

    public static Response post(String url, String json) throws IOException {
        return postJson(client, url, json);
    }

    public static Response postText(String url, String json) throws IOException {
        return postJson(client, url, json, TEXT);
    }

    public static Response postMultipart(String url, String json) throws IOException {
        return postJson(client, url, json, MULTIPART);
    }

    /**
     * 文件上传
     */
    public static Response postMultipart(String url, String fileUrl, Map<String, String> params) throws Exception {
        File file = url2File(fileUrl);
        //根据文件url转成File
        return postMultipart(client, url, file, params);
    }

    private static File url2File(String fileUrl) throws Exception {
        File file = File.createTempFile("net_url", UUID.randomUUID().toString());
        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream(file);

        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();

        return file;
    }

    private static Response get(OkHttpClient client, String url) throws IOException {
        Request request = (new Request.Builder()).url(url).build();
        return client.newCall(request).execute();
    }

    /**
     * 同步get请求
     */
    public static Response get(String url, Map<String, Object> params) {
        try {
            url = getRequestUrl(url, params);
            Request request = new Request.Builder().url(url).build();
            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new CommonException(ErrorCodeEnum.SYSTEM_FAIL.getCode(), e.getMessage());
        }
    }

    private static Response postParam(OkHttpClient httpClient, String url, Map<String, String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::add);
        FormBody body = builder.build();
        Request request = (new Request.Builder()).url(url).post(body).build();
        return httpClient.newCall(request).execute();
    }

    private static Response postJson(OkHttpClient httpClient, String url, String json, MediaType mediaType) throws IOException {
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = (new Request.Builder()).url(url).post(body).build();
        return httpClient.newCall(request).execute();
    }

    private static Response postJson(OkHttpClient httpClient, String url, String json) throws IOException {
        return postJson(client, url, json, JSON);
    }

    /**
     * 文件上传
     */
    private static Response postMultipart(OkHttpClient httpClient, String url, File file, Map<String, String> params) throws IOException {
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody requestBody = RequestBody.create(mediaType, file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("file", file.getName(), requestBody);
        params.forEach(builder::addFormDataPart);
        MultipartBody multipartBody = builder.build();

        Request request = (new Request.Builder()).url(url).post(multipartBody).build();
        return httpClient.newCall(request).execute();
    }

    /**
     * get方式URL拼接
     */
    private static String getRequestUrl(String url, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return url;
        } else {
            StringBuilder newUrl = new StringBuilder(url);
            newUrl.append("?rd=").append(random.nextInt(100000));
            for (Map.Entry<String, Object> item : params.entrySet()) {
                if (StrUtil.isNotBlank(item.getKey().trim())) {
                    try {
                        newUrl.append("&").append(item.getKey().trim()).append("=").append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
                    } catch (Exception e) {
                        throw new CommonException(ErrorCodeEnum.SYSTEM_FAIL);
                    }
                }
            }
            return newUrl.toString();
        }
    }

    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(MAX_REQUESTS);
        dispatcher.setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);
        client = (new OkHttpClient.Builder()).connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS).readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS).build();
        random = new SecureRandom();
    }
}
