package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sun.security.pkcs11.P11Util;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

@Aspect
@Configuration
public class CacheAspect {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    //添加缓存
    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object addRedisCache(ProceedingJoinPoint proceedingJoinPoint) {
        /*
         * 序列化解决redis乱码
         * */
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        //字符串拼接
        StringBuilder sb = new StringBuilder();
        //获取类的权限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        sb.append(className);
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        //获取实参
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        String key = sb.toString();
        //判断缓存中是否有数据
        Boolean b = redisTemplate.hasKey(key);
        Object result = null;
        if (b) {
            //有数据 缓存中取出数据
            result = redisTemplate.opsForValue().get(key);
        } else {
            //没有数据 查询数据库 取出数据 加入缓存
            //放行方法查询数据库
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //获取放行方法返回结果，加入缓存
            redisTemplate.opsForValue().set(key, result);
        }
        return result;
    }

    //删除缓存
    @After("@annotation(com.baizhi.annotation.DelCache)")
    public void DelRedisCache(JoinPoint joinPoint) {
        //获取类的权限定名
        String className = joinPoint.getTarget().getClass().getName();
        //获取所有的KEY
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            //判断key是否以className开头
            if (key.startsWith(className)) {
                stringRedisTemplate.delete(key);
            }
        }
    }

}
