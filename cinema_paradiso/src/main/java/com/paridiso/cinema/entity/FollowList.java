package com.paridiso.cinema.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FollowList", uniqueConstraints = @UniqueConstraint(columnNames = {"followListId"}))
public class FollowList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followListId;
    @Column
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> userIds;

    public FollowList(){userIds = new ArrayList<>(); }

    public Integer getFollowListId() {
        return followListId;
    }

    public void setFollowListId(Integer followListId) {
        this.followListId = followListId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

}
