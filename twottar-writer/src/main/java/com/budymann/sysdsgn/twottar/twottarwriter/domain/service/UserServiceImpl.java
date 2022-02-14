package com.budymann.sysdsgn.twottar.twottarwriter.domain.service;

import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.User;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.helper.CalendarHelper;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.interfaces.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private StringRedisTemplate stringRedisTemplate;
    private KeyUtils keyUtils;

    public UserServiceImpl(StringRedisTemplate srt, KeyUtils ku){
        stringRedisTemplate = srt;
        keyUtils = ku;
    }

    public User addUser(String name, String email, String description){
        var user = getUserByName(name);
        if(user != null){
            return user;
        }

        String uid = String.valueOf(stringRedisTemplate.boundValueOps(keyUtils.globalUid()).increment());
        var redisHash = stringRedisTemplate.boundHashOps(keyUtils.uid(uid));
        redisHash.put("userId", uid);
        redisHash.put("name", name);
        redisHash.put("email", email);
        redisHash.put("description", description);

        Long joinedTime = System.currentTimeMillis();
        redisHash.put("joinedTime", joinedTime.toString());

        stringRedisTemplate.boundValueOps(keyUtils.user(name)).append(uid);
        stringRedisTemplate.boundListOps(keyUtils.users()).leftPush(name);

        return getUserByUid(uid);
    }

    public User getUserByUid(String uid){
        var redisHash = stringRedisTemplate.boundHashOps(keyUtils.uid(uid));
        var user = new User();
        user.setUserId((String) redisHash.get("userId"));
        user.setName((String) redisHash.get("name"));
        user.setEmail((String) redisHash.get("email"));
        user.setDescription((String) redisHash.get("description"));

        var a = redisHash.get("joinedTime");
        user.setJoinedTime(CalendarHelper.unixToCalendar((Long) a));

        return user;
    }

    public User getUserByName(String name){
        var uid = stringRedisTemplate.boundValueOps(keyUtils.user(name)).get();
        return getUserByUid(uid);
    }


    public Set<String> getFollowing(String uid) {
        return stringRedisTemplate.opsForZSet().range(keyUtils.following(uid), 0, -1);
    }


    //hold up this is a zset
    public Set<String> getFollowers(String uid) {
        return stringRedisTemplate.opsForZSet().range(keyUtils.followers(uid), 0, -1);
    }

    public void followUser(String currentUserId, String followingId) {
        Long time = System.currentTimeMillis();
        stringRedisTemplate.boundZSetOps(keyUtils.following(currentUserId)).add(followingId, time.doubleValue());
        stringRedisTemplate.boundZSetOps(keyUtils.followers(followingId)).add(currentUserId, time.doubleValue());
    }
}
