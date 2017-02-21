package com.jsz.peini.model.square;

import java.util.List;

/**
 * Created by th on 2016/12/30.
 */

public class UserAllInfo {

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
        return "UserAllInfo{" +
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
        private String id;
        private int userLoginId;
        private String nickname;
        private String signWord;
        private String spaceBgImg;
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
        private Object isRank;

        public String getSpaceBgImg() {
            return spaceBgImg;
        }

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

        public Object getIsRank() {
            return isRank;
        }

        public void setIsRank(Object isRank) {
            this.isRank = isRank;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "id='" + id + '\'' +
                    ", userLoginId=" + userLoginId +
                    ", nickname='" + nickname + '\'' +
                    ", signWord='" + signWord + '\'' +
                    ", spaceBgImg='" + spaceBgImg + '\'' +
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
                    ", isRank=" + isRank +
                    '}';
        }
    }

    public static class TaskLastInfoBean {
        private int taskId;
        private int taskScore;
        private String taskName;
        private int sellerBigType;

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getTaskScore() {
            return taskScore;
        }

        public void setTaskScore(int taskScore) {
            this.taskScore = taskScore;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getSellerBigType() {
            return sellerBigType;
        }

        public void setSellerBigType(int sellerBigType) {
            this.sellerBigType = sellerBigType;
        }

        @Override
        public String toString() {
            return "TaskLastInfoBean{" +
                    "taskId=" + taskId +
                    ", taskScore=" + taskScore +
                    ", taskName='" + taskName + '\'' +
                    ", sellerBigType=" + sellerBigType +
                    '}';
        }
    }

    public static class SquareLastInfoBean {
        private String content;
        private String imageSrc;
        private int squareId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        public int getSquareId() {
            return squareId;
        }

        public void setSquareId(int squareId) {
            this.squareId = squareId;
        }

        @Override
        public String toString() {
            return "SquareLastInfoBean{" +
                    "content='" + content + '\'' +
                    ", imageSrc='" + imageSrc + '\'' +
                    ", squareId=" + squareId +
                    '}';
        }
    }

    public static class OtherInfoBean {
        private String signStatus;
        private String selfCount;
        private int credit;
        private String myConcern;
        private String myFans;
        private String gold;
        private String score;

        public String getSignStatus() {
            return signStatus;
        }

        public String getSelfCount() {
            return selfCount;
        }

        public int getCredit() {
            return credit;
        }

        public String getMyConcern() {
            return myConcern;
        }

        public String getMyFans() {
            return myFans;
        }

        public String getGold() {
            return gold;
        }

        public String getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "OtherInfoBean{" +
                    "signStatus=" + signStatus +
                    ", selfCount=" + selfCount +
                    ", credit=" + credit +
                    ", myConcern=" + myConcern +
                    ", myFans=" + myFans +
                    ", gold=" + gold +
                    ", score=" + score +
                    '}';
        }
    }

    public static class ImageListBean {
        private int id;
        private String imageSrc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        @Override
        public String toString() {
            return "ImageListBean{" +
                    "id=" + id +
                    ", imageSrc='" + imageSrc + '\'' +
                    '}';
        }
    }

    public static class LableListBean {
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
