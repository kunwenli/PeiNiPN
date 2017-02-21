package com.jsz.peini.model.map;

import java.io.Serializable;
import java.util.List;

/**
 * Created by th on 2016/12/14.
 */

public class BaiduMapBean implements Serializable {


    private int resultCode;
    private String resultDesc;
    private List<TaskMapListBean> taskMapList;

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

    public List<TaskMapListBean> getTaskMapList() {
        return taskMapList;
    }

    public void setTaskMapList(List<TaskMapListBean> taskMapList) {
        this.taskMapList = taskMapList;
    }

    @Override
    public String toString() {
        return "BaiduMapBean{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                ", taskMapList=" + taskMapList +
                '}';
    }

    public static class TaskMapListBean {

        private int id;
        private int sum;
        private String idStr;
        private List<TaskObjectBean> taskObject;

        public String getIdStr() {
            return idStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<TaskObjectBean> getTaskObject() {
            return taskObject;
        }

        public void setTaskObject(List<TaskObjectBean> taskObject) {
            this.taskObject = taskObject;
        }

        @Override
        public String toString() {
            return "TaskMapListBean{" +
                    "id=" + id +
                    ", sum=" + sum +
                    ", taskObject=" + taskObject +
                    '}';
        }

        public static class TaskObjectBean implements Serializable {
            /**
             * id : 83
             * userId : 1
             * imageHead :
             * xpoint : 38.048242
             * ypoint : 114.530355
             * status : 0
             */

            private int id;
            private String userId;
            private String imageHead;
            private String xpoint;
            private String ypoint;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getImageHead() {
                return imageHead;
            }

            public void setImageHead(String imageHead) {
                this.imageHead = imageHead;
            }

            public String getXpoint() {
                return xpoint;
            }

            public void setXpoint(String xpoint) {
                this.xpoint = xpoint;
            }

            public String getYpoint() {
                return ypoint;
            }

            public void setYpoint(String ypoint) {
                this.ypoint = ypoint;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            @Override
            public String toString() {
                return "TaskObjectBean{" +
                        "id=" + id +
                        ", userId=" + userId +
                        ", imageHead='" + imageHead + '\'' +
                        ", xpoint='" + xpoint + '\'' +
                        ", ypoint='" + ypoint + '\'' +
                        ", status=" + status +
                        '}';
            }
        }
    }
}
