package org.cst.buildingnotice.entity;

public class Advise {
    private String name;

    private String depict;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict == null ? null : depict.trim();
    }
}