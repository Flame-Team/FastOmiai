package com.sogou.fastomiai.util;

import java.util.Map;

public class NetworkUtil {
    
    public static String getUrl(String url, Map<String, String> params) {
        if (!url.endsWith("&")) url += "&";

        StringBuilder builder = new StringBuilder(url);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append('&');
        }

        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }
    
}
