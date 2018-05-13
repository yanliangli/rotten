package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FollowYouList", uniqueConstraints = @UniqueConstraint(columnNames = {"followYouListId"}))
public class FollowYouList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followYouListId;
    @Column
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> userIds;

    public FollowYouList(){ userIds = new ArrayList<>(); }

    public Integer getFollowYouListId() {
        return followYouListId;
    }

    public void setFollowYouListId(Integer followYouListId) {
        this.followYouListId = followYouListId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
