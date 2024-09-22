package com.example.weatherwindknow.myservice;

import java.util.List;

public class Forecast {
    private String city;           // 城市名称
    private String adcode;         // 城市编码
    private String province;       // 省份名称
    private String reporttime;     // 预报发布时间
    private List<Cast> casts;      // 预报数据 list 结构，包含当天、第二天、第三天的预报数据

    public static class Cast {

        private String date;           // 日期
        private String week;           // 星期几
        private String dayweather;     // 白天天气现象
        private String nightweather;   // 晚上天气现象
        private String daytemp;        // 白天温度
        private String nighttemp;      // 晚上温度
        private String daywind;        // 白天风向
        private String nightwind;      // 晚上风向
        private String daypower;       // 白天风力
        private String nightpower;     // 晚上风力
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getDayweather() {
            return dayweather;
        }

        public void setDayweather(String dayweather) {
            this.dayweather = dayweather;
        }

        public String getNightweather() {
            return nightweather;
        }

        public void setNightweather(String nightweather) {
            this.nightweather = nightweather;
        }

        public String getDaytemp() {
            return daytemp;
        }

        public void setDaytemp(String daytemp) {
            this.daytemp = daytemp;
        }

        public String getNighttemp() {
            return nighttemp;
        }

        public void setNighttemp(String nighttemp) {
            this.nighttemp = nighttemp;
        }

        public String getDaywind() {
            return daywind;
        }

        public void setDaywind(String daywind) {
            this.daywind = daywind;
        }

        public String getNightwind() {
            return nightwind;
        }

        public void setNightwind(String nightwind) {
            this.nightwind = nightwind;
        }

        public String getDaypower() {
            return daypower;
        }

        public void setDaypower(String daypower) {
            this.daypower = daypower;
        }

        public String getNightpower() {
            return nightpower;
        }

        public void setNightpower(String nightpower) {
            this.nightpower = nightpower;
        }

    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }


}
