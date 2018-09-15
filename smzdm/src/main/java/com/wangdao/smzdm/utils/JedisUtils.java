package com.wangdao.smzdm.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtils {

    static JedisPool pool;

    static {
        pool = new JedisPool();
    }

    public static Long scard(String key) {

        Long v = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            v = jedis.scard(key);
        } catch (Exception e) {
            System.out.println("e" + e.getCause());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return v;
    }

    public static Boolean sismember(String key, String value) {
        Jedis jedis = null;
        Boolean ret = null;
        try {
            jedis = pool.getResource();
            ret = jedis.sismember(key, value);
        } catch (Exception e) {
            System.out.println("e" + e.getCause());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return ret;
    }

    public static void sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } catch (Exception e) {
            System.out.println("e" + e.getCause());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem(key, value);
        } catch (Exception e) {
            System.out.println("e" + e.getCause());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}

