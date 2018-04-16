package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "RatedMovieList", uniqueConstraints = @UniqueConstraint(columnNames = {"ratedMovieListId"}))
public class RatedMovieList extends LinkedList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratedMovieListId;

    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(
            name = "RatedMovieList",
            joinColumns = {@JoinColumn(name = "ratedMovieListId")},
            inverseJoinColumns = {@JoinColumn(name = "imdbId")}
    )
    private List<UserRatedMovie> movies;
    public RatedMovieList() {
        movies = new ArrayList<>();
    }

    public Integer getRatedMovieListId(){
        return ratedMovieListId;
    }

    public List<UserRatedMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<UserRatedMovie> movies) {
        this.movies = movies;
    }

    public void addMovie(UserRatedMovie movie) {
        this.movies.add(movie);
    }
}
