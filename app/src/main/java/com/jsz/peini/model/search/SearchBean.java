package com.jsz.peini.model.search;

import java.util.List;

/**
 * Created by th on 2016/12/16.
 */

public class SearchBean {

    /**
     * resultCode : 1
     * resultDesc : 成功
     * wordList : [{"id":12,"hotName":"大驴","hotNum":5},{"id":1,"hotName":"德克士","hotNum":3},{"id":11,"hotName":"麻辣香锅","hotNum":2},{"id":2,"hotName":"大盘鸡","hotNum":0},{"id":3,"hotName":"美容美发","hotNum":0},{"id":4,"hotName":"KTV","hotNum":0},{"id":5,"hotName":"鸡爪","hotNum":0},{"id":6,"hotName":"猪耳朵","hotNum":0},{"id":7,"hotName":"鸡腿","hotNum":0}]
     */

    private int resultCode;
    private String resultDesc;
    private List<WordListBean> wordList;

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

    public List<WordListBean> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordListBean> wordList) {
        this.wordList = wordList;
    }

    public static class WordListBean {
        /**
         * id : 12
         * hotName : 大驴
         * hotNum : 5
         */

        private int id;
        private String hotName;
        private int hotNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHotName() {
            return hotName;
        }

        public void setHotName(String hotName) {
            this.hotName = hotName;
        }

        public int getHotNum() {
            return hotNum;
        }

        public void setHotNum(int hotNum) {
            this.hotNum = hotNum;
        }
    }
}
