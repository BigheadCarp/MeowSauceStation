package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.Friends;

import java.util.List;

public interface FriendsService extends IService<Friends> {
    List<Friends> getAllFriends();

    void saveOrUpdateFriends(Friends friends);

    void removeId(int id);
}
