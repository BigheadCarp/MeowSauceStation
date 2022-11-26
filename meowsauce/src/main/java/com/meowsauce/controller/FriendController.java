package com.meowsauce.controller;

import com.meowsauce.entity.Friends;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendsService friendsService;
    //查询所有Friend字段
    @GetMapping("/api/friends/getAllFriend")
    public Result getAllFriends(){
       List<Friends> friends =  friendsService.getAllFriends();
       return ResultFactory.buildSuccessResult(friends);
    }

    @PostMapping("/api/admin/content/friend/saveOrUpdate")
    public Result SaveOrUpdateFriend(@RequestBody Friends friends){
        friendsService.saveOrUpdateFriends(friends);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    @DeleteMapping("/api/admin/content/friend/deleteFriend/{id}")
    public Result deleteFriend(@PathVariable("id") int id){
        friendsService.removeId(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
