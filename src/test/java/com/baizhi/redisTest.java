package com.baizhi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class redisTest {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void redisTest() {
        ValueOperations string = redisTemplate.opsForValue();
        string.set("xingming", "黑", 20, TimeUnit.SECONDS);//设置失效时间
        String name = (String) string.get("xingming");
        System.out.println(name);

    }

    @Test
    public void redisTest1() {
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

    }
}
