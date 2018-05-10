package com.paridiso.cinema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.Review;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.persistence.CelebrityRepository;
import com.paridiso.cinema.persistence.MovieRepository;
import com.paridiso.cinema.persistence.TVRepository;
import com.paridiso.cinema.service.CelebrityService;
import com.paridiso.cinema.service.implementation.MovieServiceImpl;
import org.hibernate.annotations.Type;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
//@ComponentScan
@Component
public class LoadMovieData implements ApplicationRunner {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CelebrityRepository celebrityRepository;

    @Autowired
    TVRepository tvRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run(ApplicationArguments args) {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //loadAllCelebrities();
        //loadAllMovies();
        //loadTV();
        //System.out.println("load done");

    }

    private void loadTV(){
        loadTVData("data/tv/tv.json");
        loadTVPhotos("data/tv/tv_photos.json");
        loadTVTrailers("data/tv/tv_trailers.json");
        loadTVReviews("data/tv/tv_reviews.json");
    }

    private void loadAllMovies(){
        loadOscarMovies();
        loadComingSoonMovies();
        loadRecentMovies();
    }

    private void loadAllCelebrities(){
        loadCelebrityData("data/celebrity/oscar/oscar_celebrity.json");
        loadCelebrityData("data/celebrity/recent/recent_celebrity.json");
        loadCelebrityPhotos("data/celebrity/oscar/oscar_celebrity_photos.json");
        loadCelebrityPhotos("data/celebrity/recent/recent_celebrity_photos.json");
    }

    private void loadOscarMovies(){
        loadMovieData("data/movie/oscar/oscar_movie.json");
        loadMoviePhotos("data/movie/oscar/oscar_photos.json");
        loadMovieTrailers("data/movie/oscar/oscar_trailers.json");
        loadMovieReviews("data/movie/oscar/oscar_reviews.json");
    }

    private void loadComingSoonMovies(){
        loadMovieData("data/movie/coming_soon/coming_soon_movie.json");
        loadMoviePhotos("data/movie/coming_soon/coming_soon_photos.json");
        loadMovieTrailers("data/movie/coming_soon/coming_soon_trailers.json");
        loadMovieReviews("data/movie/coming_soon/coming_soon_reviews.json");
    }

    private void loadRecentMovies(){
        loadMovieData("data/movie/recent/recent_movie.json");
        loadMoviePhotos("data/movie/recent/recent_photos.json");
        loadMovieTrailers("data/movie/recent/recent_trailers.json");
        loadMovieReviews("data/movie/recent/recent_reviews.json");
    }

    private void loadTVData(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                TV tv = mapper.readValue(strLine, TV.class);
                if(tv.getPoster().equals("")){
                    tv.setPoster("http://valmorgan.co.nz/wp-content/uploads/2016/06/default-movie-1-3.jpg");
                }
                tvRepository.save(tv);
                System.out.println("Saved: " + tv.getTitle());
            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }
    private void loadTVPhotos(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                TypeReference<HashMap<String, List<String>>> typeRef
                        = new TypeReference<HashMap<String, List<String>>>() {};
                Map<String, List<String>> map = mapper.readValue(strLine, typeRef);
                String imdbId=map.get("imdbId").get(0);
                List<String> photos = map.get("photos");
                if(tvRepository.existsById(imdbId)) {
                    TV tv = tvRepository.findTVByImdbId(imdbId);
                    tv.setPhotos(photos);
                    tvRepository.save(tv);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }
    private void loadTVTrailers(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                TypeReference<HashMap<String, List<String>>> typeRef
                        = new TypeReference<HashMap<String, List<String>>>() {};
                Map<String, List<String>> map = mapper.readValue(strLine, typeRef);
                String imdbId=map.get("imdbId").get(0);
                Set<String> trailers = new HashSet<>(map.get("trailers"));
                if(tvRepository.existsById(imdbId)) {
                    TV tv = tvRepository.findTVByImdbId(imdbId);
                    tv.setTrailers(trailers);
                    tvRepository.save(tv);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }
    private void loadTVReviews(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                Review review = mapper.readValue(strLine, Review.class);
                if(tvRepository.existsById(review.getImdbId())) {
                    TV tv = tvRepository.findTVByImdbId(review.getImdbId());
                    tv.getReviews().add(review);
                    tvRepository.save(tv);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }

    }



    private void loadMovieData(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                    Movie m = mapper.readValue(strLine, Movie.class);
                    if(m.getPoster().equals("")){
                        m.setPoster("http://valmorgan.co.nz/wp-content/uploads/2016/06/default-movie-1-3.jpg");
                    }
                    movieRepository.save(m);
                    System.out.println("Saved: " + m.getTitle());
            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }

    private void loadMoviePhotos(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                TypeReference<HashMap<String, List<String>>> typeRef
                        = new TypeReference<HashMap<String, List<String>>>() {};
                Map<String, List<String>> map = mapper.readValue(strLine, typeRef);
                String imdbId=map.get("imdbId").get(0);
                List<String> photos = map.get("photos");
                if(movieRepository.existsById(imdbId)) {
                    Movie movie = movieRepository.findMovieByImdbId(imdbId);
                    movie.setPhotos(photos);
                    movieRepository.save(movie);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }

    private void loadMovieTrailers(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                TypeReference<HashMap<String, List<String>>> typeRef
                        = new TypeReference<HashMap<String, List<String>>>() {};
                Map<String, List<String>> map = mapper.readValue(strLine, typeRef);
                String imdbId=map.get("imdbId").get(0);
                Set<String> trailers = new HashSet<>(map.get("trailers"));
                if(movieRepository.existsById(imdbId)) {
                    Movie movie = movieRepository.findMovieByImdbId(imdbId);
                    movie.setTrailers(trailers);
                    movieRepository.save(movie);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }

    }
    private void loadMovieReviews(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                Review review = mapper.readValue(strLine, Review.class);
                if(movieRepository.existsById(review.getImdbId())) {
                    Movie movie = movieRepository.findMovieByImdbId(review.getImdbId());
                    movie.getReviews().add(review);
                    movieRepository.save(movie);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }

    }


    private void loadCelebrityData(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                Celebrity c = mapper.readValue(strLine, Celebrity.class);
                celebrityRepository.save(c);
                System.out.println("Saved: " + c.getName());
            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }

    private void loadCelebrityPhotos(String path){
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                TypeReference<HashMap<String, List<String>>> typeRef
                        = new TypeReference<HashMap<String, List<String>>>() {};
                Map<String, List<String>> map = mapper.readValue(strLine, typeRef);
                String id = map.get("celebrityId").get(0);
                List<String> photos = map.get("photos");
                if(celebrityRepository.existsById(id)) {
                    Celebrity celebrity = celebrityRepository.findCelebrityByCelebrityId(id);
                    celebrity.setPhotos(photos);
                    celebrityRepository.save(celebrity);
                }

            }
            br.close();
        }
        catch(FileNotFoundException fe){
            System.out.println(fe.getMessage());
        }
        catch (IOException ie){
            System.out.println(ie.getMessage());
        }
    }
}