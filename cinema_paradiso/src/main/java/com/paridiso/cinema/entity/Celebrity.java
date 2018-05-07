package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.net.URI;

@Entity
@Table(name = "Celebrities", uniqueConstraints = @UniqueConstraint(columnNames = "celebrityId"))
public class Celebrity {

    public URI PHOTO_LOCATION;


    @Id
    @Column(name = "celebrityId", unique = true)
    private String celebrityId;

    @Column(name = "name")
    private String name;

    @Column(name = "poster")
    private String poster;

    @Lob
    @Column(length = 1000)
    private String biography;

    @Column(name = "yearOfBirth")
    private String yearOfBirth;

    @Column(name = "locationOfBirth")
    private String locationOfBirth;

    @ElementCollection
    @CollectionTable(name = "CelebrityPhotos", joinColumns = @JoinColumn(name = "celebrityId"))
    @Column(name = "photos")
    private List<String> photos;

//    @Column(name = "isDirector")
//    private boolean isDirector;

    @ElementCollection
    @CollectionTable(name = "CelebrityFlims", joinColumns = @JoinColumn(name = "celebrityId"))
    @Column(name = "knownFor")
    private List<String> knownFor;

    public Celebrity() {
        try {
            setPHOTO_LOCATION(new URI("/tmp/celebrity"));
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public URI getPHOTO_LOCATION() {
        try {
            PHOTO_LOCATION = new URI("/tmp/celebrity");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return PHOTO_LOCATION;
    }

    public void setPHOTO_LOCATION(URI PHOTO_LOCATION) {
        this.PHOTO_LOCATION = PHOTO_LOCATION;
    }

    public String getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String profileImage) { this.poster = profileImage; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getProfileImageName() {
//        return poster;
//    }
//
//    public void setProfileImageName(String profileImageName) {
//        this.poster = profileImageName;
//    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String  getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String birthDate) {
        this.yearOfBirth = birthDate;
    }


//    public List<String> getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(List<String> photos) {
//        this.photos = photos;
//    }
//
//    public boolean isDirector() {
//        return this.isDirector;
//    }
//
//    public void setDirector(boolean director) {
//        isDirector = director;
//    }

    public String getLocationOfBirth() {
        return this.locationOfBirth;
    }

    public void setLocationOfBirth(String location) {
        this.locationOfBirth = location;
    }


    public List<String> getKnownFor() {
        return this.knownFor;
    }

    public void setKnownFor(List<String> filmography) {
        this.knownFor = filmography;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
