package com.paridiso.cinema.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paridiso.cinema.entity.enumerations.Genre;
import com.paridiso.cinema.entity.enumerations.Rated;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@MappedSuperclass
public class Film {

    public static final String PHOTO_LOCATION = "/tmp";

    @Id
    @Column(name = "imdbId", unique = true)
    private String imdbId;

    @Column(name = "title")
    //@JsonProperty("Title")
    private String title;

    @Column(name = "year")
    //@JsonProperty("Year")
    private String year;

    @Enumerated
    @Column(name = "rated")
    //@JsonProperty("Rated")
    private Rated rated;

    @Column(name = "releasedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
   // @JsonProperty("Released")
    private Date releaseDate;

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "MovieGenres", joinColumns = @JoinColumn(name = "imdbId"))
    @Column(name = "genre")
    //@JsonProperty("Genre")
    private List<Genre> genres;

   // @ElementCollection
    //@CollectionTable(name = "MovieAwards", joinColumns = @JoinColumn(name = "imdbId"))
    @Column(name = "award")
    //@JsonProperty("Awards")
    private String awards;

    @ElementCollection
    @CollectionTable(name = "MoviePhotos", joinColumns = @JoinColumn(name = "imdbId"))
    @Column(name = "photo")
    //@JsonProperty("images")
    private List<String> photos;

    //@Column(name = "director")
    //@JsonProperty("Director")
    @ElementCollection
    @CollectionTable(name = "MovieDirector", joinColumns = @JoinColumn(name = "imdbId"))
    private List<String> director;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            }, mappedBy = "movie")
////    @JoinTable(
////            name = "FilmsCelebrities",
////            joinColumns = {@JoinColumn(name = "imdbId")},
////            inverseJoinColumns = {@JoinColumn(name = "name")}
////    )
    @ElementCollection
    @CollectionTable(name = "MovieCasts", joinColumns = @JoinColumn(name = "imdbId"))
    //@JsonProperty("Actors")
    private List<String> casts;

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "movie")
    private Set<Trailer> trailers;

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Review> reviews;

    //@Column(name = "plot")
  //  @JsonProperty("Plot")
    @Lob
    @Column(length = 1000)
    private String plot;

    @Column(name = "language")
   // @JsonProperty("Language")
    private String language;

    @Column(name = "country")
    private String country;

    @Column(name = "poster")
    private String poster;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "numberOfRatings")
    private Integer numberOfRatings;

    @Column(name = "production")
    private String production;

    @Column(name = "website")
    private URI website;

    public Film() {
    }

    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public URI getWebsite() {
        return website;
    }

    public void setWebsite(URI website) {
        this.website = website;
    }

    public static String getPhotoLocation() {
        return PHOTO_LOCATION;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Rated getRated() {
        return rated;
    }

    public void setRated(Rated rated) {
        this.rated = rated;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getCast() {
        return casts;
    }

    public void setCast(List<String> cast) {
        this.casts = cast;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Set<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(Set<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

}
