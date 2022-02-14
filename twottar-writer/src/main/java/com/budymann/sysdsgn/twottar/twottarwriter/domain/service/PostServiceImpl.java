package com.budymann.sysdsgn.twottar.twottarwriter.domain.service;

import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.Post;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.helper.CalendarHelper;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PostServiceImpl {
    private StringRedisTemplate stringRedisTemplate;
    private KeyUtils keyUtils;

    @Autowired
    UserService userService;

    public PostServiceImpl(StringRedisTemplate srt, KeyUtils ku) {
        stringRedisTemplate = srt;
        keyUtils = ku;
    }

    public Post addPost(String uid, String content){
        return addPost(uid, content, null, null);
    }

    public Post addPost(String uid, String content, String replyPostId, String retweetUserId){
        String pid = String.valueOf(stringRedisTemplate.boundValueOps(keyUtils.globalPid()).increment());
        var postRedisHash = stringRedisTemplate.boundHashOps(keyUtils.post(pid));
        postRedisHash.put("pid", pid);
        postRedisHash.put("uid", uid);
        postRedisHash.put("content", content);

        if(replyPostId != null){
            postRedisHash.put("replyPid", replyPostId);
        }

        if(retweetUserId != null){
            postRedisHash.put("retweetUserId", retweetUserId);
        }

        Long postTime = System.currentTimeMillis();
        postRedisHash.put("postTime", postTime.toString());

        stringRedisTemplate.boundListOps(keyUtils.posts(uid)).leftPush(pid);
        stringRedisTemplate.boundZSetOps(keyUtils.timeline(uid)).add(pid, postTime);

        var followers = userService.getFollowers(uid);

        for(var followerUid : followers){
            stringRedisTemplate.boundZSetOps(keyUtils.timeline(uid)).add(followerUid, postTime);
        }

        return getPost(pid);
    }

    public Post getPost(String pid){
        var postRH = stringRedisTemplate.boundHashOps(keyUtils.post(pid));

        var post = new Post();
        post.setPostId(pid);
        post.setUserId((String) postRH.get("uid"));
        post.setContent((String) postRH.get("content"));
        post.setPostTime(CalendarHelper.unixToCalendar((Long) postRH.get("postTime")));

        if(postRH.hasKey("replyPid")){
            post.setReplyPostId((String) postRH.get("replyPid"));
        }

        if(postRH.hasKey("retweetUserId")){
            post.setRetweetUserId((String) postRH.get("retweetUserId"));
        }

        return post;
    }

    public Set<Post> getUserTimeline(String pid){


    }
}
