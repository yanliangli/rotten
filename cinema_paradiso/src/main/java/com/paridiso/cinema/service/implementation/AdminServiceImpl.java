package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.CriticApplication;
import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.entity.UserProfile;
import com.paridiso.cinema.entity.VerificationToken;
import com.paridiso.cinema.entity.enumerations.Role;
import com.paridiso.cinema.persistence.CriticApplicationRepository;
import com.paridiso.cinema.persistence.UserProfileRepository;
import com.paridiso.cinema.persistence.UserRepository;
import com.paridiso.cinema.service.UserService;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class AdminServiceImpl extends UserService {

    @Autowired
    private UtilityServiceImpl utilityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CriticApplicationRepository applicationRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Transactional
    public Optional<User> signup(User user) {
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword(utilityService.getHashedPassword(user.getPassword(), salt));
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER EXISTS");
        }
        // first create a user_profile for the user;
        UserProfile userProfile = new UserProfile();
        userProfile.setName(user.getUsername());
        user.setUserProfile(userProfileRepository.save(userProfile));
        return Optional.ofNullable(userRepository.save(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
//                .stream()
//                .filter(user -> user.getRole().equals(Role.ROLE_CRITIC) || user.getRole().equals(Role.ROLE_USER))
//                .collect(Collectors.toList());
    }

    public List<CriticApplication> getAllApplications(){
        return applicationRepository.findAll();
    }

    public boolean suspendUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "CANNOT FIND USER"));
        user.setAccountSuspended(true);
        return userRepository.save(user) != null ? true : false;
    }

    public UserProfile makeUserCritic(Integer id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CANNOT FIND PROFILE " + id));

        profile.setCritic(true);
        return userProfileRepository.save(profile);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {

    }

    @Override
    public void createVerificationToken(User user, String token) {

    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return null;
    }
}
