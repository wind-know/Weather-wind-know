package com.example.weatherwindknow.myservice;

import java.util.List;

public class Root {
    private String status;        // 返回状态，值为"1"表示成功，"0"表示失败
    private String count;         // 返回结果总数目
    private String info;          // 返回的状态信息
    private String infocode;      // 返回状态说明, "10000"代表正确
    private List<Lives> lives;    // 实况天气数据信息列表
    private List<Forecast> forecasts;  // 预报天气信息数据列表

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<Lives> getLives() {
        return lives;
    }

    public void setLives(List<Lives> lives) {
        this.lives = lives;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}