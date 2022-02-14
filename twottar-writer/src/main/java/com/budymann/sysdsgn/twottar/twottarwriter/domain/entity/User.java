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
public class User {
    private String userId;
    private String name;
    private String email;
    private String description;
    private Calendar joinedTime;
}
