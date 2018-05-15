package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.UserProfile;
import com.paridiso.cinema.service.JwtTokenService;
import com.paridiso.cinema.service.implementation.FollowerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/follower")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FollowerContoller {
    @Autowired
    FollowerImpl followerImpl;

    @Autowired
    JwtTokenService jwtTokenService;

    @RequestMapping(value = "/get/followers", method = GET)
    public ResponseEntity<List<UserProfile>> getFollowers(@RequestHeader(value = "Authorization") String jwtToken) {
        return ResponseEntity.ok(followerImpl.getFollowers(jwtTokenService.getUserIdFromToken(jwtToken)));
    }
    @RequestMapping(value = "/get/followers/{userId}", method = GET)
    public ResponseEntity<List<UserProfile>> getFollowers(@PathVariable Integer userId) {
        return ResponseEntity.ok(followerImpl.getFollowers(userId));
    }

    @RequestMapping(value = "/get/followYou", method = GET)
    public ResponseEntity<List<UserProfile>> getFollowYou(@RequestHeader(value = "Authorization") String jwtToken) {
        System.out.println("Coming get/followYou");
        return ResponseEntity.ok(followerImpl.getFollowYou(jwtTokenService.getUserIdFromToken(jwtToken)));
    }
    @RequestMapping(value = "/get/followYou/{userId}", method = GET)
    public ResponseEntity<List<UserProfile>> getFollowYou(@PathVariable Integer userId) {
        return ResponseEntity.ok(followerImpl.getFollowYou(userId));
    }

    @RequestMapping(value = "/addFollowList/{followPersonId}", method = POST)
    public ResponseEntity<Boolean> addFollowList(@RequestHeader(value = "Authorization") String jwtToken,
                                                 @PathVariable Integer followPersonId) {
        if(jwtTokenService.getUserIdFromToken(jwtToken) == followPersonId){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(followerImpl.addFollower(jwtTokenService.getUserIdFromToken(jwtToken), followPersonId));
    }
    @RequestMapping(value = "/removeFromFollowList/{followPersonId}", method = DELETE)
    public ResponseEntity<Boolean> removeFromFollowList(@RequestHeader(value = "Authorization") String jwtToken,
                                                      @PathVariable Integer followPersonId) {
        return ResponseEntity.ok(followerImpl.removeFromFollowers(jwtTokenService.getUserIdFromToken(jwtToken), followPersonId));
    }

    @GetMapping(value = "/check/{followPersonId}")
    public ResponseEntity<Boolean> checkWishlist(@RequestHeader(value = "Authorization") String jwtToken,
                                                 @PathVariable Integer followPersonId) {
        return ResponseEntity.ok(followerImpl.checkInFollowers(jwtTokenService.getUserIdFromToken(jwtToken), followPersonId));
    }

}
