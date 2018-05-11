package com.paridiso.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paridiso.cinema.entity.*;
import com.paridiso.cinema.event.OnRegistrationCompleteEvent;
import com.paridiso.cinema.persistence.UserRepository;
import com.paridiso.cinema.security.JwtTokenGenerator;
import com.paridiso.cinema.security.JwtTokenValidator;
import com.paridiso.cinema.security.JwtUser;
import com.paridiso.cinema.service.JwtTokenService;
import com.paridiso.cinema.service.implementation.RegUserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegUserController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegUserServiceImpl userService;

    @Autowired
    private JwtTokenGenerator generator;

    @Autowired
    private JwtTokenValidator validator;

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private static final Logger logger = LogManager.getLogger(RegUserController.class);

    @PostMapping(value = "/login")
    public ResponseEntity<JwtUser> userLogin(@RequestParam(value = "email", required = true) String email,
                                             @RequestParam(value = "password", required = true) String password) {
        User user = userService.login(email, password).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "USER NOT FOUND"));
        if(user.getEnabled() == false){
            return ResponseEntity.ok(null);
        }else {
            JwtUser jwtUser = new JwtUser(user.getUsername(), generator.generate(user), user.getUserID(), user.getRole());
            return ResponseEntity.ok(jwtUser);
        }

    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<Boolean> forgotPassword(HttpServletRequest request,
                                          WebRequest webRequest,
                                          @RequestParam("email") String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            throw new ResponseStatusException(BAD_REQUEST, "USER NOT FOUND");
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(request.getLocale(), token, user));
        return ResponseEntity.ok(true);
    }

    private SimpleMailMessage constructResetTokenEmail(
            Locale locale, String token, User user) {
        String url = "http://localhost:8080/user/changePassword?id=" +
                user.getUserID() + "&token=" + token;
        String message = "ResetPassword by the link: ";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(@RequestParam("id") long id, @RequestParam("token") String token) {
        String result = userService.validatePasswordResetToken(id, token);
        if (result != null) {
            return "Failed to reset the password, time expired";
        }
        return "Your password reset as your email address! \nPlease login to your account and change your password in your personal page!";
    }


    @PostMapping(value = "/logout")
    public ResponseEntity<Boolean> userLogout() {
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<JwtUser> userSignup(@RequestBody User user,
                                              WebRequest webRequest) {
        System.out.println(user.getUsername());
        System.out.println(user.getUserID());

        User optionalUser = userService.signup(user).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "USER ALREADY EXISTS"));
        System.out.println(optionalUser.getUserID());
        JwtUser jwtUser = new JwtUser(optionalUser.getUsername(),
                generator.generate(optionalUser), optionalUser.getUserID(), optionalUser.getRole());
        try {
            String appUrl = webRequest.getContextPath();
            System.out.println("the appUrl is: " + appUrl);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (optionalUser, webRequest.getLocale(), appUrl));
        } catch (Exception me) {
            System.out.println("Email err.");
            return null;
        }
        return ResponseEntity.ok(jwtUser);
    }

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {
        System.out.println("Coming regitrationConfirm");
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "redirect:/badUser.html";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Time expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "Verify succeed, you can now login to our website with email account: "+user.getEmail();
    }

    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestHeader(value = "Authorization") String jwtToken){
        return ResponseEntity.ok(userService.deleteUser(jwtTokenService.getUserIdFromToken(jwtToken)));
    }

    @GetMapping(value = "/check/username/{username}")
    public ResponseEntity<?> checkUsername(@PathVariable String username) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        boolean nameTaken = userService.checkUserNameTaken(username);
        objectNode.put("taken", nameTaken);
        return ResponseEntity.ok(objectNode);
    }

    @GetMapping(value = "/check/email/{email}")
    public ResponseEntity<?> checkUserEmail(@PathVariable String email) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        boolean nameTaken = userService.checkEmailTaken(email);
        objectNode.put("taken", nameTaken);
        return ResponseEntity.ok(objectNode);
    }

    @PostMapping(value = "/change/password")
    public ResponseEntity<?> changePassword(@RequestHeader(value = "Authorization") String jwtToken,
                                            @RequestParam(value = "old_password", required = true) String oldPass,
                                            @RequestParam(value = "new_password", required = true) String newPass) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        int headerLength = environment.getProperty("token.type").length();
        User validatedUser = validator.validate(jwtToken.substring(headerLength));
        objectNode.put("success", userService.updatePassword(validatedUser.getUserID(), oldPass, newPass));
        return ResponseEntity.ok(objectNode);
    }

    // TODO: Sending image to the backend....

    //    @PreAuthorize("hasRole('ROLE_USER')")
//    @PostMapping(value = "/protected/change/avatar")
//    public ResponseEntity<Boolean> changeProfilePicture(@RequestHeader(value = "Authorization") String jwtToken) {
//        int headerLength = environment.getProperty("token.type").length();
//        User validatedUser = validator.validate(jwtToken.substring(headerLength));
//
//        // Now you can get the user information with the data.
//        return ResponseEntity.ok(true);
//    }

    @RequestMapping(value = "/getRatedMovie/{filmId}", method = GET)
    public ResponseEntity<Double> getRatedMovie(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable String filmId)  {
        Double rating = userService.getRatedMovie(jwtTokenService.getUserIdFromToken(jwtToken), filmId);
        System.out.println("Rating is: "+rating);
        return ResponseEntity.ok(rating);
    }

    @RequestMapping(value = "/getRatedMovies", method = GET)
    public ResponseEntity<List<Movie>> getRatedMovies(@RequestHeader(value = "Authorization") String jwtToken)  {
        System.out.println("coming01");
        List<Movie> movies = userService.getRatedMovies(jwtTokenService.getUserIdFromToken(jwtToken));
        return ResponseEntity.ok(movies);
    }


    @GetMapping(value = "/get/profile")
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization") String jwtToken) {
        UserProfile profile = userService.getProfile(jwtToken);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", profile.getName());
        objectNode.put("id", profile.getId());
        if (profile.getProfileImage() == null) {
            objectNode.put("profileImage", "default.jpeg");
        } else {
            objectNode.put("profileImage", profile.getProfileImage());
        }
        objectNode.put("biography", profile.getBiography());
        objectNode.put("isCritic", profile.getCritic());
        objectNode.put("registeredDate",profile.getRegisteredDate().toString());
        return ResponseEntity.ok(objectNode);
    }


    @GetMapping(value = "/avatar/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatar(@PathVariable String fileName) throws IOException {
        String fileLocation = Paths.get("avatars/" + fileName).toAbsolutePath().toString();
        logger.info(fileLocation);

        try {
            File file = new File(fileLocation);
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RESOURCE NOT FOUND");
        }
    }

    @PostMapping(value = "/update/avatar")
    public ResponseEntity<?> upload(@RequestParam MultipartFile file,
                                    @RequestHeader(value = "Authorization") String jwtToken) throws IOException {
        return ResponseEntity.ok(userService.chagneProfilePicture(jwtToken, file));
    }


    @PostMapping(value = "/forgot/password")
    public ResponseEntity<User> verifyCritic(@RequestParam(value = "email", required = true) String email) {
        userService.forgotPassword(email);
        return null;
    }

    @PostMapping(value = "/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfile userProfile) {
        UserProfile newProfile = userService.updateProfile(userProfile);
        return new ResponseEntity<>(newProfile, HttpStatus.OK);
    }


    @PostMapping(value = "/set/private")
    public ResponseEntity<?> makeSummaryPrivate(@RequestHeader(value = "Authorization") String jwtToken) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("success", userService.makeSummaryPrivate(jwtToken));
        return ResponseEntity.ok(objectNode);
    }
}



