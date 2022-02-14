package com.budymann.sysdsgn.twottar.twottarwriter.domain.repository;

import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.User;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.service.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisZSet;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RedisHelper {

    StringRedisTemplate template;

    KeyUtils keyUtils;

    private final ValueOperations<String, String> valueOps;

    private final RedisAtomicLong postIdCounter;
    private final RedisAtomicLong userIdCounter;

    // global users
    private RedisList<String> users;
    // global timeline
    private final RedisList<String> timeline;

    @Autowired
    public RedisHelper(StringRedisTemplate redisTemplate, KeyUtils ku){
        template = redisTemplate;
        keyUtils = ku;
        valueOps = redisTemplate.opsForValue();

        /**
         * The reason we put these variables in constructor is because we want these varaibles to be accessible to another functions
         * otherwise we cna just use bound<dataset>ops
         */
        users = new DefaultRedisList<String>(keyUtils.users(), template);
        timeline = new DefaultRedisList<String>(keyUtils.timeline(), template);
        userIdCounter = new RedisAtomicLong(keyUtils.globalUid(), template.getConnectionFactory());
        postIdCounter = new RedisAtomicLong(keyUtils.globalPid(), template.getConnectionFactory());
    }

    public void addUser(User user){
        String uid = String.valueOf(template.boundValueOps(keyUtils.globalUid()).increment());
        template.boundHashOps(keyUtils.user(uid)).put("userName","");

    }

    public void followUser(String currentUserId, String followingId){
        Long time = System.currentTimeMillis();
        template.boundZSetOps(keyUtils.following(currentUserId)).add(followingId, time.doubleValue());
        template.boundZSetOps(keyUtils.followers(followingId)).add(currentUserId, time.doubleValue());
    }


    public Set<String> getFollowing(String userId){
        return template.opsForZSet().range(keyUtils.following(userId), 0, -1);
    }



}
