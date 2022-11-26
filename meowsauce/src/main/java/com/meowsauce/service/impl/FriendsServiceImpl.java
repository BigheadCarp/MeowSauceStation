package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.Friends;
import com.meowsauce.mapper.FriendsMapper;
import com.meowsauce.redis.RedisService;
import com.meowsauce.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements FriendsService {
    @Autowired
    private FriendsMapper friendsMapper;
    @Autowired
    private RedisService redisService;
    //查询所有朋友信息
    @Override
    public List<Friends> getAllFriends() {
        String keys = "getAllFriends";
        Object redisCache = redisService.get(keys);
        List<Friends> friends;
        if (redisCache == null){
             friends = friendsMapper.selectList(null);
            redisService.set(keys,friends);
        }else {
            friends = (List<Friends>)redisCache;
        }
        return friends;
    }

    @Override
    public void saveOrUpdateFriends(Friends friends) {
        if (friends.getId() == null){
            save(friends);
        }else {
            updateById(friends);
        }
        redisService.delete("getAllFriends");
    }

    @Override
    public void removeId(int id) {
        redisService.delete("getAllFriends");
        removeById(id);
    }

}
