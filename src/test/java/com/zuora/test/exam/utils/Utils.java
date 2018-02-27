package com.zuora.test.exam.utils;

import java.net.URLEncoder;
import java.util.Map;

public class Utils {

    public static String createUrl(String baseUrl, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(baseUrl);
        if (params != null && !params.isEmpty()) {
            sb.append('?');
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    String encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                    sb.append(entry.getKey()).append('=').append(encodedValue).append('&');
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
