package com.example.weatherwindknow.myservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//使用
//import com.example.weatherwindknow.myservice.WeatherService; // 确保替换为AppService接口的实际包路径
//
//// 创建AppService接口的实例
//WeatherService WeatherService = ServiceCreator.create(WeatherService.class);
public class ServiceCreator {
    // 定义基础URL
    private static final String BASE_URL = "https://restapi.amap.com/";

    // 静态内部类单例，用于创建Retrofit实例
    private static class Holder {
        private static final Retrofit RETROFIT_INSTANCE = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // 私有构造函数，防止外部实例化
    private ServiceCreator() {
    }

    // 泛型方法，用于创建服务接口的实例
    public static <T> T create(Class<T> serviceClass) {
        return Holder.RETROFIT_INSTANCE.create(serviceClass);
    }
}