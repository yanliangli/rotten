package com.paridiso.cinema.entity;

import javax.persistence.*;

@Entity
@Table(name = "CriticApplications", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class CriticApplication {
    @Id
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "userId", unique = true)
    private Integer userId;

    public CriticApplication(){

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
