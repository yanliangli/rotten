package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "Reviews", uniqueConstraints = @UniqueConstraint(columnNames = {"reviewId", "imdbId"}))
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;


    @JoinColumn(name = "imdbId", nullable = false)
    private String imdbId;

    private String movieTitle;

    private Integer userId;

    private String title;

    private String author;

    private Calendar postedDate;

    private Integer likeCount;

    private boolean isCriticReview;

    private String reviewContent;


    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isCriticReview() {
        return isCriticReview;
    }

    public void setCriticReview(boolean criticReview) {
        isCriticReview = criticReview;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Calendar getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Calendar postedDate) {
        this.postedDate = postedDate;
    }

    public String getMovieTitle(){return movieTitle;}

    public void setMovieTitle(String movieTitle){this.movieTitle = movieTitle;}
}
