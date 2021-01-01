package com.simple.retrofitutils;

/**
 * author: style12520@163.com
 * date：2020/12/17 on 9:27 PM
 * description:
 */
public class WeatherBean {

    /**
     * weatherinfo : {"city":"太仓","cityid":"101190408","temp":"22.8","WD":"东风","WS":"小于3级","SD":"81%","AP":"1005.5hPa","njd":"暂无实况","WSE":"<3","time":"17:55","sm":"3.2","isRadar":"0","Radar":""}
     */

    public WeatherinfoBean weatherinfo;

    public static class WeatherinfoBean {
        /**
         * city : 太仓
         * cityid : 101190408
         * temp : 22.8
         * WD : 东风
         * WS : 小于3级
         * SD : 81%
         * AP : 1005.5hPa
         * njd : 暂无实况
         * WSE : <3
         * time : 17:55
         * sm : 3.2
         * isRadar : 0
         * Radar :
         */

        public String city;
        public String cityid;
        public String temp;
        public String WD;
        public String WS;
        public String SD;
        public String AP;
        public String njd;
        public String WSE;
        public String time;
        public String sm;
        public String isRadar;
        public String Radar;
    }
}
