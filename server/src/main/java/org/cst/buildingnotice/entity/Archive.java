package org.cst.buildingnotice.entity;

import java.util.Date;

public class Archive {
    private Integer id;

    private String unit;

    private String phone;

    private String material;

    private String addr;

    private String hold;

    private String holdid;

    private String attr;

    private Integer layer;

    private Date createyear;

    private Integer typeid;

    private Integer rankid;

    private Double rankratio;

    private Date identitytime;

    private Integer userid;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getHold() {
        return hold;
    }

    public void setHold(String hold) {
        this.hold = hold == null ? null : hold.trim();
    }

    public String getHoldid() {
        return holdid;
    }

    public void setHoldid(String holdid) {
        this.holdid = holdid == null ? null : holdid.trim();
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Date getCreateyear() {
        return createyear;
    }

    public void setCreateyear(Date createyear) {
        this.createyear = createyear;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getRankid() {
        return rankid;
    }

    public void setRankid(Integer rankid) {
        this.rankid = rankid;
    }

    public Double getRankratio() {
        return rankratio;
    }

    public void setRankratio(Double rankratio) {
        this.rankratio = rankratio;
    }

    public Date getIdentitytime() {
        return identitytime;
    }

    public void setIdentitytime(Date identitytime) {
        this.identitytime = identitytime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}