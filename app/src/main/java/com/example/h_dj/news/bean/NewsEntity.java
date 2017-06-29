package com.example.h_dj.news.bean;

import java.util.List;

/**
 * Created by H_DJ on 2017/6/28.
 */

public class NewsEntity {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * postid : PHOT252AN000100A
         * hasCover : false
         * hasHead : 1
         * replyCount : 30616
         * hasImg : 1
         * digest :
         * hasIcon : false
         * docid : 9IG74V5H00963VRO_CO1TCDGFbjxunjianguoupdateDoc
         * title : 轿车撞护栏 乘客当场被甩飞
         * order : 1
         * priority : 354
         * lmodify : 2017-06-28 20:03:27
         * boardid : photoview_bbs
         * ads : [{"title":"小学生迟到遇奏国歌 站立雨中敬礼","skipID":"00AP0001|2263271","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/4a171b593bbd4dcfb677987ed1de553720170628164251.jpeg","subtitle":"","skipType":"photoset","url":"00AP0001|2263271"},{"title":"茅台酒厂招300名工人 30万大学生报名 ","skipID":"00AP0001|2263392","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/985de6fd64c946da8314529f85eaba3a20170628212813.jpeg","subtitle":"","skipType":"photoset","url":"00AP0001|2263392"},{"title":"四川再发山体垮塌 1人被巨石砸中身亡","skipID":"00AP0001|2263315","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/e1fcb72fe0d94b35a72f83a2567e922420170628133857.png","subtitle":"","skipType":"photoset","url":"00AP0001|2263315"},{"title":"毕业季\"制服大片\" 一张照片挤八家空姐","skipID":"00AP0001|2263336","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/ac4711242eed4c1193e468db7effb88320170628161301.jpeg","subtitle":"","skipType":"photoset","url":"00AP0001|2263336"},{"title":"广西党员穿军装重走革命道路迎\"七一\"","skipID":"00AP0001|2263316","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/02d6615e9f9b40e094fd0fadc4f169e020170628134725.jpeg","subtitle":"","skipType":"photoset","url":"00AP0001|2263316"}]
         * photosetID : 00AP0001|2263383
         * imgsum : 5
         * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
         * template : normal1
         * votecount : 28279
         * skipID : 00AP0001|2263383
         * alias : Top News
         * skipType : photoset
         * cid : C1348646712614
         * hasAD : 1
         * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/9817f9bfbde041808d823ea532c29f2420170628195645.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/d4907814e79e438497f3f24a23e8f4e420170628195646.jpeg"}]
         * source : 网易综合
         * ename : androidnews
         * tname : 头条
         * imgsrc : http://cms-bucket.nosdn.127.net/7cc64797fe7d4870afd277c668fb6ec820170628195646.jpeg
         * ptime : 2017-06-28 19:57:14
         * url_3w : http://news.163.com/17/0628/16/CO1HC28V00018AOQ.html
         * ltitle : 习近平主持中央政治局会议 审议巡视中管高校报告
         * url : http://3g.163.com/news/17/0628/16/CO1HC28V00018AOQ.html
         * subtitle :
         * specialID : S1498014591876
         */

        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String photosetID;
        private int imgsum;
        private String topic_background;
        private String template;
        private int votecount;
        private String skipID;
        private String alias;
        private String skipType;
        private String cid;
        private int hasAD;
        private String source;
        private String ename;
        private String tname;
        private String imgsrc;
        private String ptime;
        private String url_3w;
        private String ltitle;
        private String url;
        private String subtitle;
        private String specialID;
        private List<AdsBean> ads;
        private List<ImgextraBean> imgextra;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getPhotosetID() {
            return photosetID;
        }

        public void setPhotosetID(String photosetID) {
            this.photosetID = photosetID;
        }

        public int getImgsum() {
            return imgsum;
        }

        public void setImgsum(int imgsum) {
            this.imgsum = imgsum;
        }

        public String getTopic_background() {
            return topic_background;
        }

        public void setTopic_background(String topic_background) {
            this.topic_background = topic_background;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getUrl_3w() {
            return url_3w;
        }

        public void setUrl_3w(String url_3w) {
            this.url_3w = url_3w;
        }

        public String getLtitle() {
            return ltitle;
        }

        public void setLtitle(String ltitle) {
            this.ltitle = ltitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSpecialID() {
            return specialID;
        }

        public void setSpecialID(String specialID) {
            this.specialID = specialID;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public List<ImgextraBean> getImgextra() {
            return imgextra;
        }

        public void setImgextra(List<ImgextraBean> imgextra) {
            this.imgextra = imgextra;
        }

        public static class AdsBean {
            /**
             * title : 小学生迟到遇奏国歌 站立雨中敬礼
             * skipID : 00AP0001|2263271
             * tag : photoset
             * imgsrc : http://cms-bucket.nosdn.127.net/4a171b593bbd4dcfb677987ed1de553720170628164251.jpeg
             * subtitle :
             * skipType : photoset
             * url : 00AP0001|2263271
             */

            private String title;
            private String skipID;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String skipType;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSkipID() {
                return skipID;
            }

            public void setSkipID(String skipID) {
                this.skipID = skipID;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getSkipType() {
                return skipType;
            }

            public void setSkipType(String skipType) {
                this.skipType = skipType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ImgextraBean {
            /**
             * imgsrc : http://cms-bucket.nosdn.127.net/9817f9bfbde041808d823ea532c29f2420170628195645.jpeg
             */

            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }
    }
}
