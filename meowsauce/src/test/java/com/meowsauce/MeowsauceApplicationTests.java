package com.meowsauce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meowsauce.entity.Article;
import com.meowsauce.entity.User;
import com.meowsauce.mapper.ArticleMapper;
import com.meowsauce.mapper.UserMapper;
import com.meowsauce.util.IpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MeowsauceApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void contextLoads() throws Exception {
        Page<Article> page = new Page<>(1,2);
        Page<Article> page1 = articleMapper.selectPage(page, null);
        List<Article> records = page1.getRecords();
        records.forEach(e->{
            System.out.println(e.getArticleContentMd());
        });
        page1.getRecords();
    }

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(1663247943).substring(5, 7));
        System.out.println(format.format(1663247943));
    }

    public static String reverse(String originSr){
        if (originSr == null || originSr.length() <= 1)
            return originSr;
        return reverse(originSr.substring(1)) + originSr.charAt(0);
    }

}
