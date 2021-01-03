package model;

import java.util.Date;

public class IncrementalResult {

    private String sha256;

    private Date afterDummyMain;

    private Date afterRA;

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Date getAfterDummyMain() {
        return afterDummyMain;
    }

    public void setAfterDummyMain(Date afterDummyMain) {
        this.afterDummyMain = afterDummyMain;
    }

    public Date getAfterRA() {
        return afterRA;
    }

    public void setAfterRA(Date afterRA) {
        this.afterRA = afterRA;
    }
}
