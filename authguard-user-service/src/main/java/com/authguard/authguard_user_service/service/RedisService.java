package com.authguard.authguard_user_service.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * Service for performing generic Redis cache operations.
 *
 * <p>This service uses JSON serialization (via {@link ObjectMapper}) to store and retrieve
 * objects in Redis. It supports setting a time-to-live (TTL) for cached entries.</p>
 *
 * <p>Example usage:
 * <pre>
 *     redisService.saveToCache("user:123", userObject, Duration.ofMinutes(5));
 *     User cachedUser = redisService.getFromCache("user:123", User.class);
 * </pre>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Saves an object to Redis as JSON.
     *
     * @param key   the Redis key under which the data will be stored
     * @param data  the object to cache
     * @param ttl   the time-to-live duration before the key expires
     * @param <T>   the type of the object to store
     * @throws JsonProcessingException if serialization fails
     */
    public <T> void saveToCache(String key, T data, Duration ttl) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(data);
        redisTemplate.opsForValue().set(key, json, ttl);
    }

    /**
     * Retrieves an object from Redis and deserializes it from JSON.
     *
     * @param key         the Redis key to look up
     * @param targetClass the class type to deserialize into
     * @param <T>         the type of the returned object
     * @return the cached object, or {@code null} if not found
     * @throws JsonProcessingException if deserialization fails
     */
    public <T> T getFromCache(String key, Class<T> targetClass) throws JsonProcessingException {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) {
            return null;
        }
        return objectMapper.readValue(json, targetClass);
    }
}
