package com.example.h_dj.news.bean;

import java.util.List;

/**
 * Created by H_DJ on 2017/5/30.
 */

public class WeatherInfos {

    /**
     * pubdate : 2017-05-30
     * pubtime : 18:00:00
     * time : 1496145595
     * area : [["广东","28"],["广州","2801"],["从化","101280103"]]
     * weather : [{"date":"2017-05-30","info":{"day":["1","多云","32","无持续风向","微风"],"night":["1","多云","23","无持续风向","微风"]}},{"date":"2017-05-31","info":{"dawn":["1","多云","23","无持续风向","微风"],"day":["1","多云","32","无持续风向","微风"],"night":["4","雷阵雨","24","无持续风向","微风"]}},{"date":"2017-06-01","info":{"dawn":["4","雷阵雨","24","无持续风向","微风"],"day":["4","雷阵雨","31","西南风","4-5 级"],"night":["9","大雨","23","无持续风向","微风"]}},{"date":"2017-06-02","info":{"dawn":["9","大雨","23","无持续风向","微风"],"day":["9","大雨","28","无持续风向","微风"],"night":["22","中雨-大雨","23","无持续风向","微风"]}},{"date":"2017-06-03","info":{"dawn":["22","中雨-大雨","23","无持续风向","微风"],"day":["22","中雨-大雨","28","无持续风向","微风"],"night":["4","雷阵雨","22","无持续风向","微风"]}},{"date":"2017-06-04","info":""},{"date":"2017-06-05","info":""}]
     * life : {"date":"2017-5-30","info":{"chuanyi":["炎热","天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"],"ganmao":["少发","各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"],"kongtiao":["部分时间开启","天气热同时湿度大，您需要适当开启制冷空调，来降温除湿，免受闷热之苦。"],"wuran":["中","气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较不宜","天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。"],"ziwaixian":["中等","属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"]}}
     * realtime : {"city_code":"101280103","city_name":"从化","date":"2017-05-30","time":"19:00:00","week":2,"moon":"","dataUptime":1496144824,"weather":{"temperature":"25","humidity":"92","info":"多云","img":"1"},"wind":{"direct":"南风","power":"2级","offset":null,"windspeed":null}}
     * pm25 : []
     */

    private String pubdate;
    private String pubtime;
    private int time;
    private LifeBean life;
    private RealtimeBean realtime;
    private List<List<String>> area;
    private List<WeatherBeanX> weather;
    private List<?> pm25;

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public LifeBean getLife() {
        return life;
    }

    public void setLife(LifeBean life) {
        this.life = life;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public List<List<String>> getArea() {
        return area;
    }

    public void setArea(List<List<String>> area) {
        this.area = area;
    }

    public List<WeatherBeanX> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBeanX> weather) {
        this.weather = weather;
    }

    public List<?> getPm25() {
        return pm25;
    }

    public void setPm25(List<?> pm25) {
        this.pm25 = pm25;
    }

    public static class LifeBean {
        /**
         * date : 2017-5-30
         * info : {"chuanyi":["炎热","天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"],"ganmao":["少发","各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"],"kongtiao":["部分时间开启","天气热同时湿度大，您需要适当开启制冷空调，来降温除湿，免受闷热之苦。"],"wuran":["中","气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较不宜","天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。"],"ziwaixian":["中等","属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"]}
         */

        private String date;
        private InfoBean info;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            private List<String> chuanyi;
            private List<String> ganmao;
            private List<String> kongtiao;
            private List<String> wuran;
            private List<String> xiche;
            private List<String> yundong;
            private List<String> ziwaixian;

            public List<String> getChuanyi() {
                return chuanyi;
            }

            public void setChuanyi(List<String> chuanyi) {
                this.chuanyi = chuanyi;
            }

            public List<String> getGanmao() {
                return ganmao;
            }

            public void setGanmao(List<String> ganmao) {
                this.ganmao = ganmao;
            }

            public List<String> getKongtiao() {
                return kongtiao;
            }

            public void setKongtiao(List<String> kongtiao) {
                this.kongtiao = kongtiao;
            }

            public List<String> getWuran() {
                return wuran;
            }

            public void setWuran(List<String> wuran) {
                this.wuran = wuran;
            }

            public List<String> getXiche() {
                return xiche;
            }

            public void setXiche(List<String> xiche) {
                this.xiche = xiche;
            }

            public List<String> getYundong() {
                return yundong;
            }

            public void setYundong(List<String> yundong) {
                this.yundong = yundong;
            }

            public List<String> getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(List<String> ziwaixian) {
                this.ziwaixian = ziwaixian;
            }
        }
    }

    public static class RealtimeBean {
        /**
         * city_code : 101280103
         * city_name : 从化
         * date : 2017-05-30
         * time : 19:00:00
         * week : 2
         * moon :
         * dataUptime : 1496144824
         * weather : {"temperature":"25","humidity":"92","info":"多云","img":"1"}
         * wind : {"direct":"南风","power":"2级","offset":null,"windspeed":null}
         */

        private String city_code;
        private String city_name;
        private String date;
        private String time;
        private int week;
        private String moon;
        private int dataUptime;
        private WeatherBean weather;
        private WindBean wind;

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getMoon() {
            return moon;
        }

        public void setMoon(String moon) {
            this.moon = moon;
        }

        public int getDataUptime() {
            return dataUptime;
        }

        public void setDataUptime(int dataUptime) {
            this.dataUptime = dataUptime;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class WeatherBean {
            /**
             * temperature : 25
             * humidity : 92
             * info : 多云
             * img : 1
             */

            private String temperature;
            private String humidity;
            private String info;
            private String img;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public String toString() {
                return "WeatherBean{" +
                        "temperature='" + temperature + '\'' +
                        ", humidity='" + humidity + '\'' +
                        ", info='" + info + '\'' +
                        ", img='" + img + '\'' +
                        '}';
            }
        }

        public static class WindBean {
            /**
             * direct : 南风
             * power : 2级
             * offset : null
             * windspeed : null
             */

            private String direct;
            private String power;
            private Object offset;
            private Object windspeed;

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }

            public Object getOffset() {
                return offset;
            }

            public void setOffset(Object offset) {
                this.offset = offset;
            }

            public Object getWindspeed() {
                return windspeed;
            }

            public void setWindspeed(Object windspeed) {
                this.windspeed = windspeed;
            }
        }
    }

    public static class WeatherBeanX {
        /**
         * date : 2017-05-30
         * info : {"day":["1","多云","32","无持续风向","微风"],"night":["1","多云","23","无持续风向","微风"]}
         */

        private String date;
        private InfoBeanX info;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public InfoBeanX getInfo() {
            return info;
        }

        public void setInfo(InfoBeanX info) {
            this.info = info;
        }

        public static class InfoBeanX {
            private List<String> day;
            private List<String> night;

            public List<String> getDay() {
                return day;
            }

            public void setDay(List<String> day) {
                this.day = day;
            }

            public List<String> getNight() {
                return night;
            }

            public void setNight(List<String> night) {
                this.night = night;
            }
        }
    }
}
