package com.budymann.sysdsgn.twottar.twottarwriter.domain.service;

import org.springframework.stereotype.Service;

@Service
public class KeyUtils {
    public final String UID = "uid:";

    public String following(String uid) {
        return UID + uid + ":following";
    }

    public String followers(String uid) {
        return UID + uid + ":followers";
    }

    String timeline(String uid) {
        return UID + uid + ":timeline";
    }

    public String mentions(String uid) {
        return UID + uid + ":mentions";
    }

    public String posts(String uid) {
        return UID + uid + ":posts";
    }

    public String auth(String uid) {
        return UID + uid + ":auth";
    }

    public String uid(String uid) {
        return UID + uid;
    }

    public String post(String pid) {
        return "pid:" + pid;
    }

    public String authKey(String auth) {
        return "auth:" + auth;
    }

    public String user(String name) {
        return "user:" + name + ":uid";
    }

    public String users() {
        return "users";
    }

    public String timeline() {
        return "timeline";
    }

    public String globalUid() {
        return "global:uid";
    }

    public String globalPid() {
        return "global:pid";
    }

    public String alsoFollowed(String uid, String targetUid) {
        return UID + uid + ":also:uid:" + targetUid;
    }

    public String commonFollowers(String uid, String targetUid) {
        return UID + uid + ":common:uid:" + targetUid;
    }
}
