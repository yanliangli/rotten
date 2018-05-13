package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.*;
import com.paridiso.cinema.entity.enumerations.Role;
import com.paridiso.cinema.persistence.*;

import com.paridiso.cinema.security.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import com.paridiso.cinema.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeFunction.function;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class RegUserServiceImpl extends UserService {
    @Autowired
    private passwordTokenRepository passwordTokenRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    ReviewServiceImpl reviewService;

    @Autowired
    private UtilityServiceImpl utilityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtTokenValidator validator;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    WatchListRepository watchListRepository;

    @Autowired
    FollowListRepository followListRepository;

    @Autowired
    FollowYouListRepository followYouListRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    FollowerImpl followerImpl;


    private Integer counter;

    @Transactional
    public Optional<User> signup(User user) {
        user.setEnabled(false);
        user.setRole(Role.ROLE_USER);
        user.setAccountSuspended(false);
        user.setPassword(utilityService.getHashedPassword(user.getPassword(), salt));
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER EXISTS");
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setName(user.getUsername());
        user.setUserProfile(userProfileRepository.save(userProfile));
        user.getUserProfile().setWishList(wishListRepository.save(new WishList()));
        user.getUserProfile().setWatchList(watchListRepository.save(new WatchList()));
        user.getUserProfile().setFollowList(followListRepository.save(new FollowList()));
        user.getUserProfile().setFollowYouList(followYouListRepository.save(new FollowYouList()));
        user.getUserProfile().setRegisteredDate();
        return Optional.ofNullable(userRepository.save(user));
    }

    @Transactional
    public UserProfile updateProfile(UserProfile userProfile) {
        UserProfile profile = userProfileRepository.findById(userProfile.getId())
                .orElseThrow(() -> new RuntimeException("CANNOT FIND PROFILE " + userProfile.getId()));
        profile.setBiography(userProfile.getBiography());
        profile.setWatchList(userProfile.getWatchList());
        profile.setWishList(userProfile.getWishList());
        profile.setName(userProfile.getName());
        profile.setProfileImage(userProfile.getProfileImage());
        profile.setPrivate(userProfile.getPrivate());
        return userProfileRepository.save(profile);
    }



    @Transactional
    public boolean makeSummaryPrivate(String jwtToken) {
        int headerLength = environment.getProperty("token.type").length();
        User validatedUser = validator.validate(jwtToken.substring(headerLength));
        UserProfile profile = userProfileRepository.findById(validatedUser.getUserProfile().getId())
                .orElseThrow(() -> new RuntimeException("CANNOT FIND PROFILE"));
        profile.setPrivate(true);
        return userProfileRepository.save(profile).getPrivate() == true ? true : false;
    }

    @Transactional
    public boolean chagneProfilePicture(String jwtToken, MultipartFile file) throws IOException {
        int headerLength = environment.getProperty("token.type").length();
        User validatedUser = validator.validate(jwtToken.substring(headerLength));

        UserProfile profile = userProfileRepository.findById(validatedUser.getUserProfile().getId())
                .orElseThrow(() -> new RuntimeException("CANNOT FIND PROFILE"));

        profile.setProfileImage(validatedUser.getUserProfile().getId() + ".jpeg");
        System.out.println(profile);
        UserProfile profile1 = userProfileRepository.save(profile);

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("avatars/" + profile.getId() + ".jpeg");
            System.out.println(path.toAbsolutePath().toString());
            Files.write(path, bytes);
            return true;
        } else {
            return false;
        }
    }


    @Transactional
    public boolean updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        String hashedPassword = utilityService.getHashedPassword(oldPassword, salt);
        if (!hashedPassword.equals(user.getPassword())) {
            return false;
        } else {
            user.setPassword(utilityService.getHashedPassword(newPassword, salt));
            return userRepository.save(user).getPassword() != null ? true : false;
        }
    }

    @Transactional
    public boolean checkUserNameTaken(String userName) {
        return userRepository.findUserByUsername(userName) != null ? true : false;
    }

    @Transactional
    public boolean checkEmailTaken(String email) {
        return userRepository.findUserByEmail(email) != null ? true : false;
    }
    @Transactional
    public Double getRatedMovie(Integer userId, String filmImdbId) {
        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        // check movie existence
        List<Movie> movies = user.getUserProfile().getRatedMovies();
        List<UserRating> userRatings = user.getUserProfile().getUserRatings();
        Movie movie = movieRepository.findMovieByImdbId(filmImdbId);
        if (utilityService.containsMovie(movies, filmImdbId)){
            for(UserRating userRating:userRatings){
                if(userRating.getMovie().getImdbId().equals(movie.getImdbId())){
                    return userRating.getUserRating();
                }
            }
        }
        // RatedMovie not exist
        System.out.println("coming03" + counter);
        return 0.0;
    }


    @Transactional
    public List<Movie> getRatedMovies(Integer userId) {
        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        // get RatedMovieList and return
        List<Movie> movies = user.getUserProfile().getRatedMovies();
        System.out.println("coming01");
        return movies;
    }

    // @TODO: Map<Movie, Double>
    @Transactional
    public boolean rateMovie(Integer userId, String filmId, Double rating) {
        // get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        Movie newRatedMovie = movieRepository.findMovieByImdbId(filmId);
        // check existence
        List<Movie> movieList = user.getUserProfile().getRatedMovies();
        List<UserRating> userRatings = user.getUserProfile().getUserRatings();
        if (utilityService.containsMovie(movieList, filmId))
            return false;

        UserRating userRating = new UserRating();
        userRating.setMovie(newRatedMovie);
        userRating.setUserRating(rating);

        // add to rated movie list
        movieList.add(newRatedMovie);
        userRatings.add(userRating);
        userProfileRepository.save(user.getUserProfile());
        return true;
    }

    @Transactional
    public boolean deleteRating(Integer userId, String filmId){
        // get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        Movie rateMovieToBeRemoved = movieRepository.findMovieByImdbId(filmId);
        List<Movie> ratedMovieList = user.getUserProfile().getRatedMovies();
        List<UserRating> userRatings = user.getUserProfile().getUserRatings();
        for (Iterator<UserRating> it = userRatings.iterator();it.hasNext();){
            UserRating userRating = it.next();
            if(userRating.getMovie().getImdbId().equals(rateMovieToBeRemoved.getImdbId())){
                rateMovieToBeRemoved.setRating((double)Math.round(((rateMovieToBeRemoved.getRating()
                        *(rateMovieToBeRemoved.getNumberOfRatings()) - userRating.getUserRating())
                        / (rateMovieToBeRemoved.getNumberOfRatings()-1))*10)/10);
                ratedMovieList.remove(rateMovieToBeRemoved);
                rateMovieToBeRemoved.setNumberOfRatings(rateMovieToBeRemoved.getNumberOfRatings()-1);
                it.remove();
                movieRepository.save(rateMovieToBeRemoved);
                break;
            }
        }

        return true;
    }

    @Transactional
    public UserProfile getProfile(Integer userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "PROFILE NOT FOUND"));
    }

    @Transactional
    public boolean deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Review> reviews = user.getUserProfile().getReviews();
        if(reviews!=null){
            for(Review review: reviews){
                List<Review> reviewList = movieRepository.findMovieByImdbId(review.getImdbId()).getReviews();
                reviewList.remove(review);
            }
        }
        reviews.clear();
        List<UserRating> userRatings = user.getUserProfile().getUserRatings();
        if(userRatings!=null){
            if(userRatings.size() == 1){
                UserRating userRating = userRatings.get(0);
                this.deleteRating(userId,userRating.getMovie().getImdbId());
            }else{
                for(UserRating userRating:userRatings){
                    this.deleteRating(userId,userRating.getMovie().getImdbId());
                }
            }
        }
        List<Integer> userIds = user.getUserProfile().getFollowList().getUserIds();
        if(userIds != null){
            if(userIds.size() == 1){
                Integer toBeRemove = userIds.get(0);
                followerImpl.removeFromFollowers(userId, toBeRemove);
            }else{
                for(Integer toBeRemove:userIds){
                    followerImpl.removeFromFollowers(userId, toBeRemove);
                }
            }
        }
        user.getUserProfile().getFollowYouList().getUserIds().clear();
        user.getUserProfile().getWatchList().clear();
        user.getUserProfile().getWishList().clear();
        userProfileRepository.deleteById(userId);
        userRepository.deleteById(userId);
        return true;
    }


    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Transactional
    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passToken =
                passwordTokenRepository.findByToken(token);
        System.out.println("coming 01");
        if ((passToken == null) || (passToken.getUser()
                .getUserID() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        User user = passToken.getUser();
        user.setPassword(utilityService.getHashedPassword(user.getEmail(), salt));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}

