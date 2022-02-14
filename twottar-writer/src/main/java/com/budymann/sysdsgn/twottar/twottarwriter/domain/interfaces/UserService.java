package com.budymann.sysdsgn.twottar.twottarwriter.domain.interfaces;

import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public Set<String> getFollowing(String uid);
    public Set<String> getFollowers(String uid);

    public void followUser(String currentUserId, String followingId);

    public User addUser(String name, String email, String description);
    public User getUserByUid(String uid);
    public User getUserByName(String name);
}
