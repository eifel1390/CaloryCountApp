package com.example.calorycountapp.Model;


public class TemporaryEntity extends Entity {

    private String temporaryName;
    private int temporaryCount;
    private String entityType;


    public TemporaryEntity() {}

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public int getTemporaryCount() {
        return temporaryCount;
    }

    public void setTemporaryCount(int temporaryCount) {
        this.temporaryCount = temporaryCount;
    }

    public String getTemporaryName() {
        return temporaryName;
    }

    public void setTemporaryName(String temporaryName) {
        this.temporaryName = temporaryName;
    }


}
