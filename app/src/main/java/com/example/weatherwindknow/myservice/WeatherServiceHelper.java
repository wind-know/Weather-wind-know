package com.example.weatherwindknow.myservice;

import android.util.Log;

import java.util.List;

import kotlin.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class WeatherServiceHelper {

    private WeatherService service;

    public WeatherServiceHelper() {
        this.service = ServiceCreator.create(WeatherService.class);
    }//获取WeatherService实例
    public WeatherServiceHelper(WeatherService service) {
        this.service = service;
    }//获取WeatherService实例

    // 发送请求并返回 Call<Root> 对象
    public Call<Root> getWeatherData(String apiKey, String cityCode, String extensions) {
        return service.getWeatherData(apiKey, cityCode, extensions);
    }
    static List<Lives> Lives = null;
    // 异步执行请求并处理结果
    public void getWeatherDataAsync(String apiKey, String cityCode, String extensions,  final WeatherDataCallback callback) {
        Call<Root> call = getWeatherData(apiKey, cityCode, extensions);
        Call<Root> call1 = getWeatherData(apiKey, cityCode, "base");

        call1.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call1, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root weatherData1 = response.body();
                    Lives = weatherData1.getLives();
                    Log.d("TAGlives1", Lives==null?"null":Lives.toString());
//                    if(Lives!=null) {
//                        Log.d("TAGlives1", Lives.toString());
//                    }
                    call.enqueue(new Callback<Root>() {
                        @Override
                        public void onResponse(Call<Root> call, Response<Root> response) {
                            if (response.isSuccessful()) {
                                Root weatherData = response.body();
                                if(Lives!=null) {
                                    weatherData.setLives(Lives);
                                }
                                callback.onSuccess(weatherData);
                            } else {
                                callback.onError(new Throwable("Failed with status code: " + response.code()));
                                Log.d("TAG", "onResponse,no ");
                            }
                        }

                        @Override
                        public void onFailure(Call<Root> call, Throwable t) {
                            callback.onError(t);
                            Log.d("TAG", "NO");
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<Root> call1, Throwable t) {
                callback.onError(t);
            }
        });

    }
}
