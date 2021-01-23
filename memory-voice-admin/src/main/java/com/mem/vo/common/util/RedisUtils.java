package com.mem.vo.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

@Component
public class RedisUtils {
    private final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${redis.host}") String host,
        @Value("${redis.port}") Integer port,
        @Value("${redis.timeout}") Integer timeout,
        @Value("${redis.password}") String password) {
        if(StringUtils.isNotBlank(password)){
            return new JedisPool(jedisPoolConfig(), host,port,timeout,password);

        }
        return new JedisPool(jedisPoolConfig(), host,port);
    }

    @PostConstruct
    public void init() {
        logger.info(jedisPoolConfig.toString());
    }

    private final int DEFAULT_TIMEOUT = 60;   //60 secend

    private int timeout;

    private int checkParam(String key, int expires) {
        checkKey(key);
        return expires <= 0 ? (timeout <= 0 ? DEFAULT_TIMEOUT : timeout) : expires;
    }

    private void checkKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(key);
        }
    }

    public boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            return jedis.set(key, value).equalsIgnoreCase("ok");
        } catch (Exception e) {
            logger.error("set redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public boolean setnx(String key, String value, int expires) {
        Jedis jedis = null;
        try {
            expires = checkParam(key, expires);
            jedis = getConnection();
            String rep = jedis.set(key, value, "NX", "EX", expires);
            return "ok".equalsIgnoreCase(rep);
        } catch (Exception e) {
            logger.error("setnx redis error:key={},value={},{}", key, value, e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public boolean setex(String key, String value, int expires) {
        Jedis jedis = null;
        try {
            expires = checkParam(key, expires);
            jedis = getConnection();
            return jedis.setex(key, expires, value).equalsIgnoreCase("ok");
        } catch (Exception e) {
            logger.error("setex redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public boolean hmset(String key, Map<String, String> field, int expires) {
        Jedis jedis = null;
        try {
            expires = checkParam(key, expires);
            if (field != null && field.size() > 0) {
                jedis = getConnection();
                if (jedis.hmset(key, field).equalsIgnoreCase("ok")) {
                    return (jedis.expire(key, expires).longValue() == 1);
                }
            }
        } catch (Exception e) {
            logger.error("hmset redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public boolean lpush(String key, List<String> list, int expires) {
        Jedis jedis = null;
        try {
            expires = checkParam(key, expires);
            jedis = getConnection();
            Long len = jedis.lpush(key, list.toArray(new String[list.size()]));
            if (len == list.size()) {
                return jedis.expire(key, expires).longValue() == 1;
            }
        } catch (Exception e) {
            logger.error("lpush redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            Long len = jedis.llen(key);
            if (len.longValue() > 0)
                return jedis.lrange(key, 0, len.longValue());
        } catch (Exception e) {
            logger.error("lrange redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    public Long llen(String key) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            return jedis.llen(key);
        } catch (Exception e) {
            logger.error("llen redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }


    public String get(String key) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("get redis error :", e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    public List<String> hmget(String key, String[] fields) {
        Jedis jedis = null;
        try {
            checkKey(key);
            if (fields != null && fields.length > 0) {
                jedis = getConnection();
                return jedis.hmget(key, fields);
            }
        } catch (Exception e) {
            logger.error("get redis error :", e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    public boolean expire(String key, int expires) {
        Jedis jedis = null;
        try {
            expires = checkParam(key, expires);
            jedis = getConnection();
            return jedis.expire(key, expires).longValue() == 1;
        } catch (Exception e) {
            logger.error("expire redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public boolean del(String key) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            return jedis.del(key).longValue() == 1;
        } catch (Exception e) {
            logger.error("del redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    public boolean delByKeyValuePair(String key, String value) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            Long num = (Long) jedis.eval(luaScript, Arrays.asList(key), Arrays.asList(value));
            return num.longValue() == 1;
        } catch (Exception e) {
            logger.error("del redis error:", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    /**
     * Return jedis connection to the pool, call different return methods
     * depends on the conectionBroken status.
     */
    private void closeResource(Jedis jedis) {
        try {
            returnResource(jedis);
        } catch (Exception e) {
            logger.error("return back jedis failed, will fore close the jedis.", e);
            try {
                jedis.quit();
            } catch (Exception ex) {
                logger.error("jedis quit error :", ex);
            }
            try {
                jedis.disconnect();
            } catch (Exception exd) {
                logger.error("jedis disconnect error :", exd);
            }
        }
    }

    private Jedis getConnection() {
        if (jedisPool == null) {
            logger.error("getConnection error : jedisPool is null");
            throw new RuntimeException("getConnection error : jedisPool is null");
        }
        return jedisPool.getResource();
    }

    private void returnResource(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (JedisException e) {
                logger.error("returnConnection error :", e);
            }
        }
    }
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            return jedis.incr(key);
        } catch (Exception e) {
            logger.error("incr redis error :", e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    public Long incrBy(String key, long increment) {
        Jedis jedis = null;
        try {
            checkKey(key);
            jedis = getConnection();
            return jedis.incrBy(key, increment);
        } catch (Exception e) {
            logger.error("incrBy redis error :", e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    public boolean delKeysByPrefix(String Prefix) {
        Jedis jedis = null;
        try {
            checkKey(Prefix);
            jedis = getConnection();
            String parten = Prefix + "*";
            Set<String> set = jedis.keys(parten);
            for (String s : set) {
                jedis.del(s);
            }
            return true;
        } catch (Exception e) {
            logger.error("delKeysByPrefix redis error :", e);
        } finally {
            closeResource(jedis);
        }
        return false;
    }
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
