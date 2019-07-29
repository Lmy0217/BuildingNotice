package org.cst.buildingnotice.entity;

public class Invite {
    private String code;

    private Integer createid;

    private Integer inviteid;

    private Integer status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getCreateid() {
        return createid;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    public Integer getInviteid() {
        return inviteid;
    }

    public void setInviteid(Integer inviteid) {
        this.inviteid = inviteid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}