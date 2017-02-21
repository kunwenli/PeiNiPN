package com.jsz.peini.model.square;

import java.util.List;

/**
 * Created by th on 2017/1/2.
 */

public class UserInfoByOtherId {

    /**
     * resultCode : 1
     * resultDesc : 成功
     * userInfo : {"id":1,"userLoginId":1,"nickname":"大驴","signWord":"我是二二","imageHead":"/upload/user/1/20161211145644.png","sex":1,"sexText":"男","age":35,"height":null,"weight":null,"oldProvince":null,"oldProvinceText":null,"oldCity":null,"oldCityText":null,"oldCounty":null,"oldCountyText":null,"nowProvince":130000,"nowProvinceText":"河北省","nowCity":130100,"nowCityText":"石家庄市","nowCounty":null,"nowCountyText":null,"constellation":1,"constellationText":"白羊座","nation":1,"nationText":"汉族","smallIncome":7000,"bigIncome":null,"degree":1,"degreeText":"小学","industry":1,"industryText":"计算机/互联网/通信","isHouse":0,"isHouseText":null,"isCar":1,"isCarText":"已购车","isPhone":0,"isIdcard":1,"isVideo":0,"birthday":"2016-12-09","emotion":1,"emotionText":"单身","reputation":null,"goldList":1,"buyList":0,"integrityList":0,"selfNum":70,"idcardNum":40,"taskNum":600}
     * imageList : [{"imageSrc":"/upload/square/1/20161223151938head.png","id":2},{"imageSrc":"/upload/square/1/20161223152033head.png","id":4},{"imageSrc":"/upload/square/1/20161223152033head.png","id":5},{"imageSrc":"/upload/square/1/20161223151938head.png","id":1}]
     * lableList : [{"id":14,"labelName":"年少有为"},{"id":8,"labelName":"有车族"}]
     * taskLastInfo : {"sellerBigType":101,"taskName":"大脚","taskScore":73,"taskId":87}
     * squareLastInfo : {"squareId":75,"imageSrc":"/upload/square/1/201612291631120.png","content":"333"}
     * otherInfo : {"isConcern":1,"myConcern":6,"myFans":5,"selfCount":70,"credit":59,"mycredit":59}
     */

    private int resultCode;
    private String resultDesc;
    private UserInfoBean userInfo;
    private TaskLastInfoBean taskLastInfo;
    private SquareLastInfoBean squareLastInfo;
    private OtherInfoBean otherInfo;
    private List<ImageListBean> imageList;
    private List<LableListBean> lableList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public TaskLastInfoBean getTaskLastInfo() {
        return taskLastInfo;
    }

    public void setTaskLastInfo(TaskLastInfoBean taskLastInfo) {
        this.taskLastInfo = taskLastInfo;
    }

    public SquareLastInfoBean getSquareLastInfo() {
        return squareLastInfo;
    }

    public void setSquareLastInfo(SquareLastInfoBean squareLastInfo) {
        this.squareLastInfo = squareLastInfo;
    }

    public OtherInfoBean getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfoBean otherInfo) {
        this.otherInfo = otherInfo;
    }

    public List<ImageListBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageListBean> imageList) {
        this.imageList = imageList;
    }

    public List<LableListBean> getLableList() {
        return lableList;
    }

    public void setLableList(List<LableListBean> lableList) {
        this.lableList = lableList;
    }

    @Override
    public String toString() {
        return "UserInfoByOtherId{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", userInfo=" + userInfo +
                ", taskLastInfo=" + taskLastInfo +
                ", squareLastInfo=" + squareLastInfo +
                ", otherInfo=" + otherInfo +
                ", imageList=" + imageList +
                ", lableList=" + lableList +
                '}';
    }

    public static class UserInfoBean {
        /**
         * id : 1
         * userLoginId : 1
         * nickname : 大驴
         * signWord : 我是二二
         * imageHead : /upload/user/1/20161211145644.png
         * sex : 1
         * sexText : 男
         * age : 35
         * height : null
         * weight : null
         * oldProvince : null
         * oldProvinceText : null
         * oldCity : null
         * oldCityText : null
         * oldCounty : null
         * oldCountyText : null
         * nowProvince : 130000
         * nowProvinceText : 河北省
         * nowCity : 130100
         * nowCityText : 石家庄市
         * nowCounty : null
         * nowCountyText : null
         * constellation : 1
         * constellationText : 白羊座
         * nation : 1
         * nationText : 汉族
         * smallIncome : 7000
         * bigIncome : null
         * degree : 1
         * degreeText : 小学
         * industry : 1
         * industryText : 计算机/互联网/通信
         * isHouse : 0
         * isHouseText : null
         * isCar : 1
         * isCarText : 已购车
         * isPhone : 0
         * isIdcard : 1
         * isVideo : 0
         * birthday : 2016-12-09
         * emotion : 1
         * emotionText : 单身
         * reputation : null
         * goldList : 1
         * buyList : 0
         * integrityList : 0
         * selfNum : 70
         * idcardNum : 40
         * taskNum : 600
         */

        private String id;
        private int userLoginId;
        private String nickname;
        private String signWord;
        private String imageHead;
        private int sex;
        private String sexText;
        private int age;
        private Object height;
        private Object weight;
        private Object oldProvince;
        private Object oldProvinceText;
        private Object oldCity;
        private Object oldCityText;
        private Object oldCounty;
        private Object oldCountyText;
        private int nowProvince;
        private String nowProvinceText;
        private int nowCity;
        private String nowCityText;
        private Object nowCounty;
        private Object nowCountyText;
        private int constellation;
        private String constellationText;
        private int nation;
        private String nationText;
        private int smallIncome;
        private Object bigIncome;
        private int degree;
        private String degreeText;
        private int industry;
        private String industryText;
        private int isHouse;
        private Object isHouseText;
        private int isCar;
        private String isCarText;
        private int isPhone;
        private int isIdcard;
        private int isVideo;
        private String birthday;
        private int emotion;
        private String emotionText;
        private Object reputation;
        private int goldList;
        private int buyList;
        private int integrityList;
        private int selfNum;
        private int idcardNum;
        private int taskNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUserLoginId() {
            return userLoginId;
        }

        public void setUserLoginId(int userLoginId) {
            this.userLoginId = userLoginId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSignWord() {
            return signWord;
        }

        public void setSignWord(String signWord) {
            this.signWord = signWord;
        }

        public String getImageHead() {
            return imageHead;
        }

        public void setImageHead(String imageHead) {
            this.imageHead = imageHead;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSexText() {
            return sexText;
        }

        public void setSexText(String sexText) {
            this.sexText = sexText;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getOldProvince() {
            return oldProvince;
        }

        public void setOldProvince(Object oldProvince) {
            this.oldProvince = oldProvince;
        }

        public Object getOldProvinceText() {
            return oldProvinceText;
        }

        public void setOldProvinceText(Object oldProvinceText) {
            this.oldProvinceText = oldProvinceText;
        }

        public Object getOldCity() {
            return oldCity;
        }

        public void setOldCity(Object oldCity) {
            this.oldCity = oldCity;
        }

        public Object getOldCityText() {
            return oldCityText;
        }

        public void setOldCityText(Object oldCityText) {
            this.oldCityText = oldCityText;
        }

        public Object getOldCounty() {
            return oldCounty;
        }

        public void setOldCounty(Object oldCounty) {
            this.oldCounty = oldCounty;
        }

        public Object getOldCountyText() {
            return oldCountyText;
        }

        public void setOldCountyText(Object oldCountyText) {
            this.oldCountyText = oldCountyText;
        }

        public int getNowProvince() {
            return nowProvince;
        }

        public void setNowProvince(int nowProvince) {
            this.nowProvince = nowProvince;
        }

        public String getNowProvinceText() {
            return nowProvinceText;
        }

        public void setNowProvinceText(String nowProvinceText) {
            this.nowProvinceText = nowProvinceText;
        }

        public int getNowCity() {
            return nowCity;
        }

        public void setNowCity(int nowCity) {
            this.nowCity = nowCity;
        }

        public String getNowCityText() {
            return nowCityText;
        }

        public void setNowCityText(String nowCityText) {
            this.nowCityText = nowCityText;
        }

        public Object getNowCounty() {
            return nowCounty;
        }

        public void setNowCounty(Object nowCounty) {
            this.nowCounty = nowCounty;
        }

        public Object getNowCountyText() {
            return nowCountyText;
        }

        public void setNowCountyText(Object nowCountyText) {
            this.nowCountyText = nowCountyText;
        }

        public int getConstellation() {
            return constellation;
        }

        public void setConstellation(int constellation) {
            this.constellation = constellation;
        }

        public String getConstellationText() {
            return constellationText;
        }

        public void setConstellationText(String constellationText) {
            this.constellationText = constellationText;
        }

        public int getNation() {
            return nation;
        }

        public void setNation(int nation) {
            this.nation = nation;
        }

        public String getNationText() {
            return nationText;
        }

        public void setNationText(String nationText) {
            this.nationText = nationText;
        }

        public int getSmallIncome() {
            return smallIncome;
        }

        public void setSmallIncome(int smallIncome) {
            this.smallIncome = smallIncome;
        }

        public Object getBigIncome() {
            return bigIncome;
        }

        public void setBigIncome(Object bigIncome) {
            this.bigIncome = bigIncome;
        }

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public String getDegreeText() {
            return degreeText;
        }

        public void setDegreeText(String degreeText) {
            this.degreeText = degreeText;
        }

        public int getIndustry() {
            return industry;
        }

        public void setIndustry(int industry) {
            this.industry = industry;
        }

        public String getIndustryText() {
            return industryText;
        }

        public void setIndustryText(String industryText) {
            this.industryText = industryText;
        }

        public int getIsHouse() {
            return isHouse;
        }

        public void setIsHouse(int isHouse) {
            this.isHouse = isHouse;
        }

        public Object getIsHouseText() {
            return isHouseText;
        }

        public void setIsHouseText(Object isHouseText) {
            this.isHouseText = isHouseText;
        }

        public int getIsCar() {
            return isCar;
        }

        public void setIsCar(int isCar) {
            this.isCar = isCar;
        }

        public String getIsCarText() {
            return isCarText;
        }

        public void setIsCarText(String isCarText) {
            this.isCarText = isCarText;
        }

        public int getIsPhone() {
            return isPhone;
        }

        public void setIsPhone(int isPhone) {
            this.isPhone = isPhone;
        }

        public int getIsIdcard() {
            return isIdcard;
        }

        public void setIsIdcard(int isIdcard) {
            this.isIdcard = isIdcard;
        }

        public int getIsVideo() {
            return isVideo;
        }

        public void setIsVideo(int isVideo) {
            this.isVideo = isVideo;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getEmotion() {
            return emotion;
        }

        public void setEmotion(int emotion) {
            this.emotion = emotion;
        }

        public String getEmotionText() {
            return emotionText;
        }

        public void setEmotionText(String emotionText) {
            this.emotionText = emotionText;
        }

        public Object getReputation() {
            return reputation;
        }

        public void setReputation(Object reputation) {
            this.reputation = reputation;
        }

        public int getGoldList() {
            return goldList;
        }

        public void setGoldList(int goldList) {
            this.goldList = goldList;
        }

        public int getBuyList() {
            return buyList;
        }

        public void setBuyList(int buyList) {
            this.buyList = buyList;
        }

        public int getIntegrityList() {
            return integrityList;
        }

        public void setIntegrityList(int integrityList) {
            this.integrityList = integrityList;
        }

        public int getSelfNum() {
            return selfNum;
        }

        public void setSelfNum(int selfNum) {
            this.selfNum = selfNum;
        }

        public int getIdcardNum() {
            return idcardNum;
        }

        public void setIdcardNum(int idcardNum) {
            this.idcardNum = idcardNum;
        }

        public int getTaskNum() {
            return taskNum;
        }

        public void setTaskNum(int taskNum) {
            this.taskNum = taskNum;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "id=" + id +
                    ", userLoginId=" + userLoginId +
                    ", nickname='" + nickname + '\'' +
                    ", signWord='" + signWord + '\'' +
                    ", imageHead='" + imageHead + '\'' +
                    ", sex=" + sex +
                    ", sexText='" + sexText + '\'' +
                    ", age=" + age +
                    ", height=" + height +
                    ", weight=" + weight +
                    ", oldProvince=" + oldProvince +
                    ", oldProvinceText=" + oldProvinceText +
                    ", oldCity=" + oldCity +
                    ", oldCityText=" + oldCityText +
                    ", oldCounty=" + oldCounty +
                    ", oldCountyText=" + oldCountyText +
                    ", nowProvince=" + nowProvince +
                    ", nowProvinceText='" + nowProvinceText + '\'' +
                    ", nowCity=" + nowCity +
                    ", nowCityText='" + nowCityText + '\'' +
                    ", nowCounty=" + nowCounty +
                    ", nowCountyText=" + nowCountyText +
                    ", constellation=" + constellation +
                    ", constellationText='" + constellationText + '\'' +
                    ", nation=" + nation +
                    ", nationText='" + nationText + '\'' +
                    ", smallIncome=" + smallIncome +
                    ", bigIncome=" + bigIncome +
                    ", degree=" + degree +
                    ", degreeText='" + degreeText + '\'' +
                    ", industry=" + industry +
                    ", industryText='" + industryText + '\'' +
                    ", isHouse=" + isHouse +
                    ", isHouseText=" + isHouseText +
                    ", isCar=" + isCar +
                    ", isCarText='" + isCarText + '\'' +
                    ", isPhone=" + isPhone +
                    ", isIdcard=" + isIdcard +
                    ", isVideo=" + isVideo +
                    ", birthday='" + birthday + '\'' +
                    ", emotion=" + emotion +
                    ", emotionText='" + emotionText + '\'' +
                    ", reputation=" + reputation +
                    ", goldList=" + goldList +
                    ", buyList=" + buyList +
                    ", integrityList=" + integrityList +
                    ", selfNum=" + selfNum +
                    ", idcardNum=" + idcardNum +
                    ", taskNum=" + taskNum +
                    '}';
        }
    }

    public static class TaskLastInfoBean {
        /**
         * sellerBigType : 101
         * taskName : 大脚
         * taskScore : 73
         * taskId : 87
         */

        private int sellerBigType;
        private String taskName;
        private int taskScore;
        private int taskId;

        public int getSellerBigType() {
            return sellerBigType;
        }

        public void setSellerBigType(int sellerBigType) {
            this.sellerBigType = sellerBigType;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getTaskScore() {
            return taskScore;
        }

        public void setTaskScore(int taskScore) {
            this.taskScore = taskScore;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public String toString() {
            return "TaskLastInfoBean{" +
                    "sellerBigType=" + sellerBigType +
                    ", taskName='" + taskName + '\'' +
                    ", taskScore=" + taskScore +
                    ", taskId=" + taskId +
                    '}';
        }
    }

    public static class SquareLastInfoBean {
        /**
         * squareId : 75
         * imageSrc : /upload/square/1/201612291631120.png
         * content : 333
         */

        private int squareId;
        private String imageSrc;
        private String content;

        public int getSquareId() {
            return squareId;
        }

        public void setSquareId(int squareId) {
            this.squareId = squareId;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "SquareLastInfoBean{" +
                    "squareId=" + squareId +
                    ", imageSrc='" + imageSrc + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public static class OtherInfoBean {
        /**
         * isConcern : 1
         * myConcern : 6
         * myFans : 5
         * selfCount : 70
         * credit : 59
         * mycredit : 59
         */

        private int isConcern;
        private int myConcern;
        private int myFans;
        private int selfCount;
        private int credit;
        private int mycredit;

        public int getIsConcern() {
            return isConcern;
        }

        public void setIsConcern(int isConcern) {
            this.isConcern = isConcern;
        }

        public int getMyConcern() {
            return myConcern;
        }

        public void setMyConcern(int myConcern) {
            this.myConcern = myConcern;
        }

        public int getMyFans() {
            return myFans;
        }

        public void setMyFans(int myFans) {
            this.myFans = myFans;
        }

        public int getSelfCount() {
            return selfCount;
        }

        public void setSelfCount(int selfCount) {
            this.selfCount = selfCount;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public int getMycredit() {
            return mycredit;
        }

        public void setMycredit(int mycredit) {
            this.mycredit = mycredit;
        }

        @Override
        public String toString() {
            return "OtherInfoBean{" +
                    "isConcern=" + isConcern +
                    ", myConcern=" + myConcern +
                    ", myFans=" + myFans +
                    ", selfCount=" + selfCount +
                    ", credit=" + credit +
                    ", mycredit=" + mycredit +
                    '}';
        }
    }

    public static class ImageListBean {
        /**
         * imageSrc : /upload/square/1/20161223151938head.png
         * id : 2
         */

        private String imageSrc;
        private int id;

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "ImageListBean{" +
                    "imageSrc='" + imageSrc + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public static class LableListBean {
        /**
         * id : 14
         * labelName : 年少有为
         */

        private int id;
        private String labelName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        @Override
        public String toString() {
            return "LableListBean{" +
                    "id=" + id +
                    ", labelName='" + labelName + '\'' +
                    '}';
        }
    }
}
