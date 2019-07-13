package org.cst.buildingnotice.entity;

public class ArchiveWithBLOBs extends Archive {
    private String body1;

    private String body2;

    private String body3;

    private String advise;

    private String remark;

    public String getBody1() {
        return body1;
    }

    public void setBody1(String body1) {
        this.body1 = body1 == null ? null : body1.trim();
    }

    public String getBody2() {
        return body2;
    }

    public void setBody2(String body2) {
        this.body2 = body2 == null ? null : body2.trim();
    }

    public String getBody3() {
        return body3;
    }

    public void setBody3(String body3) {
        this.body3 = body3 == null ? null : body3.trim();
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise == null ? null : advise.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}