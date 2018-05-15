package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "WatchList", uniqueConstraints = @UniqueConstraint(columnNames = {"watchlistId"}))
public class WatchList extends LinkedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer watchListId;

    // https://stackoverflow.com/questions/14408977/hibernate-onetomany-mapping-failing
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(
            name = "WatchListsMovies",
            joinColumns = {@JoinColumn(name = "watchListId")},
            inverseJoinColumns = {@JoinColumn(name = "imdbId")}
    )
    private List<Movie> movies;

    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(
            name = "WatchListsTvs",
            joinColumns = {@JoinColumn(name = "watchListId")},
            inverseJoinColumns = {@JoinColumn(name = "imdbId")}
    )
    private List<TV> tvs;


    public WatchList() {
        movies = new ArrayList<>();
    }

    public Integer getSizeLimit() {
        return 999;
    }

    public Integer getWatchListId() {
        return watchListId;
    }

    public void setWatchListId(Integer watchListId) {
        this.watchListId = watchListId;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setTvs(List<TV> tvs) {
        this.tvs = tvs;
    }

    public List<TV> getTvs() {
        return tvs;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
