package com.fuzamei.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

/**
 * @author ylx
 * Created by fuzamei on 2018/4/22.
 */
@Component
@Slf4j
public class RedisUtil {

    private final RedisTemplate<Object,Object> redisTemplate;

    private final JedisPool jedisPool;

    @Autowired
    public RedisUtil(RedisTemplate<Object, Object> redisTemplate, JedisPool jedisPool) {
        this.redisTemplate = redisTemplate;
        this.jedisPool = jedisPool;
    }

    //=============================common============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间 long类型
     * @param timeUnit 时间单位
     * @return
     */
    public final boolean expire(String key,long time,TimeUnit timeUnit){
        try {
            if(time > 0){
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("设置redis失效时间出现异常");
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key){
        try {
            return redisTemplate.getExpire(key,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("从redis获取失效时间出现异常");
            return null;
        }
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis判断是否有key值出现异常");
            return false;
        }
    }

    /**
     * 删除缓存 (批量的key值删除)
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void delete(String ... key){
        try {
            if(key != null && key.length > 0){
                if(key.length == 1){
                    redisTemplate.delete(key[0]);
                }else{
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }
            }
        } catch (Exception e) {
            log.error("redis删除key值出现异常");
        }
    }

    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("redis获取key对应的value值出现异常");
            return null;
        }
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis插入key-value对出现异常");
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time,TimeUnit timeUnit){
        try {
            if(time > 0){
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("redis插入key-value对出现异常");
            return false;
        }
    }

    /**
     * 自增1
     * @param key
     * @return
     */
    public boolean incr(String key){
        try {
            Long increment = redisTemplate.opsForValue().increment(key, 1L);
            if(!increment.equals(1L)){
                throw new RuntimeException();
            }
            return true;
        }catch (Exception e){
            log.error("redis自增出现异常");
            return false;
        }
    }

    /**
     * 自增数量自定义
     * @param key
     * @param value
     * @return
     */
    public boolean incrBy(String key,long value){
        try {
            Long increment = redisTemplate.opsForValue().increment(key, value);
            if(!increment.equals(1L)){
                throw new RuntimeException();
            }
            return true;
        }catch (Exception e){
            log.error("redis自增出现异常");
            return false;
        }
    }

    /**
     * set一个key,如果key存在redis会返回0，如果key不存在，返回1
     * @param key
     * @param value
     * @return
     */
    public boolean setNx(String key,String value){
        Jedis jedis = jedisPool.getResource();
        try {
            String nx = jedis.set(key, value, "NX");
//            long success = jedis.setnx(key, value);
            if(nx!=null){
                return true;
            }
            return false;
        }catch (Exception e){
            log.error("redis加锁异常");
            return false;
        }finally {
            jedis.close();
        }
    }

    /**
     * stxNx
     * @return
     */
    public boolean setNx(String key,String value,long expire){
        Jedis jedis = jedisPool.getResource();
        try {
            String lock = jedis.set(key, value, "NX", "EX", expire);
            if(lock != null){
                return true;
            }
            return false;
        }catch (Exception e){
            log.error("redis加锁异常");
            return false;
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public long incrOne(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incr(key);

        }catch (Exception e){
            return 0;
        }finally {
            jedis.close();
        }
    }

    public long decrOne(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.decr(key);

        }catch (Exception e){
            return 0;
        }finally {
            jedis.close();
        }
    }



}
