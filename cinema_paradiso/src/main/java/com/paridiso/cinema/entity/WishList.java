package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "WishLists", uniqueConstraints = @UniqueConstraint(columnNames = {"wishlistId"}))
public class WishList extends LinkedList {

    private final Integer SIZE_LIMIT = 999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishlistId;

    // should not use @OneToMany because
    // https://stackoverflow.com/questions/14408977/hibernate-onetomany-mapping-failing
    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(
            name = "WishListsMovies",
            joinColumns = {@JoinColumn(name = "wishListId")},
            inverseJoinColumns = {@JoinColumn(name = "imdbId")}
    )
    private List<Movie> movies;

    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(
            name = "WishListsTvs",
            joinColumns = {@JoinColumn(name = "wishListId")},
            inverseJoinColumns = {@JoinColumn(name = "imdbId")}
    )
    private List<TV> tvs;

    public WishList() {
        movies = new ArrayList<>();
        tvs = new ArrayList<>();
    }

    public Integer getSizeLimit() {
        return SIZE_LIMIT;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<TV> getTvs() {
        return tvs;
    }

    public void setTvs(List<TV> tvs) {
        this.tvs = tvs;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

}
