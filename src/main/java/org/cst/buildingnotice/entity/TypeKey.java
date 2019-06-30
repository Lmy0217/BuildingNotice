package org.cst.buildingnotice.entity;

public class TypeKey {
    private Integer id;

    private String tabel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTabel() {
        return tabel;
    }

    public void setTabel(String tabel) {
        this.tabel = tabel == null ? null : tabel.trim();
    }
}