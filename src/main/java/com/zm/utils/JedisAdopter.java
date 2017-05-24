package com.zm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by Ellen on 2017/5/23.
 */
@Service
public class JedisAdopter implements InitializingBean{
    private static Logger logger = LoggerFactory.getLogger(JedisAdopter.class);

    private JedisPool pool = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://127.0.0.1:6379/2");
    }

    /**
     * 添加kv
     * @param key
     * @param value
     * @return
     */
    public long sadd(String key,String value) {
        try (Jedis jedis = pool.getResource()){
            return jedis.sadd(key,value);
        }catch (Exception e) {
            logger.error("获取jedis连接池失败" + e.getMessage());
        }
        return 0;
    }

    /**
     * 移出元素
     * @param key
     * @param value
     * @return
     */
    public long srem(String key,String value) {
        try(Jedis jedis = pool.getResource()){
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("获取jedis连接池失败" + e.getMessage());
        }
        return 0;
    }

    /**
     * 返回长度
     * @param key
     * @return
     */
    public long scard(String key) {
        try(Jedis jedis = pool.getResource()){
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("获取jedis连接池失败" + e.getMessage());
        }
        return 0;
    }

    /**
     * 是否存在该kv
     * @param key
     * @param value
     * @return
     */
    public Boolean ismember(String key, String value) {
        try(Jedis jedis = pool.getResource()){
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("获取jedis连接池失败" + e.getMessage());
        }
        return false;
    }

    /**
     * 弹出元素，如果元素不存在则等待
     * @param timeout
     * @param key
     * @return
     */
    public List<String> brpop(int timeout,String key) {
        try(Jedis jedis = pool.getResource()){
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("获取jedis连接池失败" + e.getMessage());
        }
        return null;
    }

    /**
     * 压入元素
     * @param key
     * @param value
     * @return
     */
    public long lpush(String key,String value) {
        try(Jedis jedis = pool.getResource()){
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("获取jedis连接池失败" + e.getMessage());
        }
        return 0;
    }
}
