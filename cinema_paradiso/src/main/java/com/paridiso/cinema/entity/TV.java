package com.paridiso.cinema.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TV",  uniqueConstraints = @UniqueConstraint(columnNames = "imdbId"))
public class TV extends Film{

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "audienceRating")
    private Double audienceRating;

    public TV() {
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getAudienceRating() {
        return audienceRating;
    }

    public void setAudienceRating(Double audienceRating) {
        this.audienceRating = audienceRating;
    }
}
