package com.budymann.sysdsgn.twottar.twottarwriter.resource.model;

import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.Post;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostModel {
    private String postId;
    private UserModel user;
    private String content;
    private String time;
    private Post replyPostId;
    private String retweetUserId;
    private String retweetBy;
    private int like;
    private int retweet;
}
