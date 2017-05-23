package com.zm.utils;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by Ellen on 2017/5/23.
 */
@Service
public class RedisUtils {
    private static Jedis jedis = new Jedis("redis://127.0.0.1:6379/2");
    public static void jedis() {
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    public static void main(String[] args) {
        jedis();
    }

    public static void set() {
        jedis.
    }
}
