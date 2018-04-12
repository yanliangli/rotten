package com.paridiso.cinema.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class CriticApplication {
    @Id
    @Column(name = "userId", unique = true)
    private Integer userId;

    public CriticApplication(){

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
