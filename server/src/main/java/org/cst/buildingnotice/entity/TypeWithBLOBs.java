package org.cst.buildingnotice.entity;

public class TypeWithBLOBs extends Type {
    private String body2;

    private String body3;

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
}