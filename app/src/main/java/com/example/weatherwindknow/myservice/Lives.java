package com.example.weatherwindknow.myservice;
public class Lives{
    private String province;     // 省份名
    private String city;         // 城市名
    private String adcode;       // 区域编码
    private String weather;      // 天气现象（汉字描述）
    private String temperature;  // 实时气温，单位：摄氏度
    private String winddirection;// 风向描述
    private String windpower;    // 风力级别，单位：级
    private String humidity;     // 空气湿度
    private String reporttime;   // 数据发布的时间
    private String temperature_float; // 实时气温，浮点数格式
    private String humidity_float;   // 空气湿度，浮点数格式

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return this.adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWinddirection() {
        return this.winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getWindpower() {
        return this.windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReporttime() {
        return this.reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getTemperature_float() {
        return this.temperature_float;
    }

    public void setTemperature_float(String temperature_float) {
        this.temperature_float = temperature_float;
    }

    public String getHumidity_float() {
        return this.humidity_float;
    }

    public void setHumidity_float(String humidity_float) {
        this.humidity_float = humidity_float;
    }
    public String toString(){
        return "省份："+province+" 城市："+city+" 区域编码："+adcode+" 天气现象："+weather+" 实时气温："+temperature+" 风向描述："+winddirection+" 风力级别："+windpower+" 空气湿度："+humidity+" 数据发布的时间："+reporttime+" 实时气温，浮点数格式："+temperature_float+" 空气湿度，浮点数格式："+humidity_float;
    }

}