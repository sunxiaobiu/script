package model;

import java.util.Date;

public class ApkInfo implements Comparable<ApkInfo>{

    private String sha256;

    private Date createTime;

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public int compareTo(ApkInfo o) {
        if(getCreateTime().compareTo(o.getCreateTime()) == 0){
            return 0;
        }else{
            return getCreateTime().compareTo(o.getCreateTime()) == 1 ? -1 : 1;
        }
    }
}
