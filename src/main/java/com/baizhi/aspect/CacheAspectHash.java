package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

public class CacheAspectHash {
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
        //hash 储存  KEY--value(key,value)  值里又有一个键值对

        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        //字符串拼接
        StringBuilder sb = new StringBuilder();
        //获取类的权限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
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
        HashOperations hash = redisTemplate.opsForHash();
        Boolean b = hash.hasKey(className, key);
        Object result = null;
        if (b) {
            //有数据 缓存中取出数据
            result = hash.get(className, key);
        } else {
            //没有数据 查询数据库 取出数据 加入缓存
            //放行方法查询数据库
            try {
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //获取放行方法返回结果，加入缓存
            hash.put(className, key, result);
        }
        return result;
    }

    //删除缓存
    @After("@annotation(com.baizhi.annotation.DelCache)")
    public void DelRedisCache(JoinPoint joinPoint) {
        //获取类的权限定名
        String className = joinPoint.getTarget().getClass().getName();
        stringRedisTemplate.delete(className);

    }

}
