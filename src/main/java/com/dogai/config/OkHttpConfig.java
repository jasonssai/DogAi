package com.dogai.config;


import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration(value = "okhttp")
public class OkHttpConfig {

    @Value("${okhttp.proxy.connectTimeout}")
    private int connectTimeout;

    @Value("${okhttp.proxy.readTimeout}")
    private int readTimeout;

    @Value("${okhttp.proxy.writeTimeout}")
    private int writeTimeout;

    @Value("${okhttp.proxy.connectionPool.maxConnect}")
    private int maxConnect;

    @Value("${okhttp.proxy.connectionPool.keepAlive}")
    private int keepAlive;

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(connectTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .connectionPool(new ConnectionPool(maxConnect, keepAlive, TimeUnit.MINUTES));
//        if (enable) {
//            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
//        }
        return builder.build();
    }
}
