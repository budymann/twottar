package com.budymann.sysdsgn.twottar.twottarwriter.resource;

import com.budymann.sysdsgn.twottar.twottarwriter.domain.entity.User;
import com.budymann.sysdsgn.twottar.twottarwriter.domain.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String helloWorld(@RequestParam("uid") String currentUserId, @RequestParam ("fuid") String followingId){
//        userService.followUser(currentUserId, followingId);
//        var followings = userService.getFollowing("1000");

        var a = userService.addUser("zawsh", "jawsh@gmail.com", "YOOHOO");
        return "HelloWorld";
    };

}
