package com.websocket.Lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private LotterySelectedUsers lotterySelectedUsers;

    @GetMapping
    public List<Users> addUsers(){
        return lotterySelectedUsers.populateUserTable();
    }

    @GetMapping("/run")
    public List<Users> runLottery(){
        return lotterySelectedUsers.runLottery();
    }
}
