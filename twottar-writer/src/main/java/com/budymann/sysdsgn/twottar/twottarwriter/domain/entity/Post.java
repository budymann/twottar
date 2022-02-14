package com.budymann.sysdsgn.twottar.twottarwriter.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String postId;
    private String userId;
    private String content;
    private Calendar postTime;
    private String replyPostId;
    private String retweetUserId;
    private int like;
    private int retweet;
}
