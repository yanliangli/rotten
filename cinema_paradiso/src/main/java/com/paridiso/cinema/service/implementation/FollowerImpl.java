package com.paridiso.cinema.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paridiso.cinema.entity.*;
import com.paridiso.cinema.persistence.FollowListRepository;
import com.paridiso.cinema.persistence.FollowYouListRepository;
import com.paridiso.cinema.persistence.UserProfileRepository;
import com.paridiso.cinema.persistence.UserRepository;
import com.paridiso.cinema.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class FollowerImpl {
    @Autowired
    FollowListRepository followListRepository;

    @Autowired
    FollowYouListRepository followYouListRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public boolean addFollower(Integer userId, Integer followPersonId) {
        System.out.println("Coming addFollowList");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        User followPerson = userRepository.findById(followPersonId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        FollowList followList = user.getUserProfile().getFollowList();
        followList.getUserIds().add(followPersonId);
        followListRepository.save(followList);
        FollowYouList followYouList = followPerson.getUserProfile().getFollowYouList();
        followYouList.getUserIds().add(userId);
        followYouListRepository.save(followYouList);
        return true;
    }

    @Transactional
    public List<UserProfile> getFollowers(Integer userId) {
        System.out.println("Coming getFollowers");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Integer> userIds = user.getUserProfile().getFollowList().getUserIds();
        List<UserProfile> userProfiles = new ArrayList<>();
        for(Integer userId1: userIds){
            UserProfile userProfile = userProfileRepository.findById(userId1)
                    .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
            System.out.println("User name = "+userProfile.getName());
            System.out.println("Id = "+userProfile.getId());
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }

    @Transactional
    public List<UserProfile> getFollowYou(Integer userId) {
        System.out.println("Coming getFollowYou");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Integer> userIds = user.getUserProfile().getFollowYouList().getUserIds();
        List<UserProfile> userProfiles = new ArrayList<>();
        for(Integer userId1: userIds){
            UserProfile userProfile = userProfileRepository.findById(userId1)
                    .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }

    @Transactional
    public boolean removeFromFollowers(Integer userId, Integer deFollowPersonId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        User deFollowPerson = userRepository.findById(deFollowPersonId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        FollowList followList = user.getUserProfile().getFollowList();
        FollowYouList followYouList = deFollowPerson.getUserProfile().getFollowYouList();
        followList.getUserIds().remove(deFollowPersonId);
        followYouList.getUserIds().remove(userId);
        followListRepository.save(followList);
        followYouListRepository.save(followYouList);
        return  true;
    }

    @Transactional
    public boolean checkInFollowers(Integer userId, Integer followPersonId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        FollowList followList = user.getUserProfile().getFollowList();
        if(followList == null) followList = new FollowList();
        if(followList.getUserIds().contains(followPersonId)){
            return true;
        }else return false;

    }

}
