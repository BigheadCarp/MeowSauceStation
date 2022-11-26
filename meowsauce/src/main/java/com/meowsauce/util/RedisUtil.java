package com.meowsauce.util;

import com.meowsauce.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil {
    @Autowired
    private RedisService redisService;

    public void deleteArticleCache(int id){
        redisService.delete("article:" + id);
        redisService.delete("articleList");
        Set<String> keys = redisService.getKeysByPattern("articlepage*");
        redisService.delete(keys);
        redisService.delete("getArticleByTime");
        redisService.delete("getArticleToHavingLooking");
        redisService.delete("getArticleTotalByType");
        redisService.delete("getCarouselMsg");
    }
}
