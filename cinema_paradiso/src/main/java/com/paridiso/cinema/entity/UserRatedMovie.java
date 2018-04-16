package com.paridiso.cinema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "UserRatedMovies",  uniqueConstraints = @UniqueConstraint(columnNames = "imdbId"))
public class UserRatedMovie extends Movie {

    @Column(name = "userRate")
    private Integer userRate;

    public UserRatedMovie(){

    }
    public Integer getUserRate(){
        return userRate;
    }
    public void setUserRate(Integer userRate){
        this.userRate = userRate;
    }
}
