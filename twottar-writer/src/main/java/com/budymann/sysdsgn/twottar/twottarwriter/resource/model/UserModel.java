package com.budymann.sysdsgn.twottar.twottarwriter.resource.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class UserModel {
    private String userId;
    private String name;
    private String twitterHandle;
    private String description;
    private Calendar joinedTime;
}
