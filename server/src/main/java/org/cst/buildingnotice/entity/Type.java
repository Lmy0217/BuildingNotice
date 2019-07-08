package org.cst.buildingnotice.entity;

public class Type {
    private Integer id;

    private String name;

    private String tabel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTabel() {
        return tabel;
    }

    public void setTabel(String tabel) {
        this.tabel = tabel == null ? null : tabel.trim();
    }
}