package com.zm.service;

import com.zm.utils.JedisAdopter;
import com.zm.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ellen on 2017/5/24.
 */
@Service
public class LikeService {
	@Autowired
	private JedisAdopter jedisAdopter;

	public long count(int entityType, int entityId) {
		String likeKey = RedisUtil.getLikeKey(entityType, entityId);
		return jedisAdopter.scard(likeKey);
	}

	public int getLikeStatus(int userId, int entityType, int entityId) {
		String likeKey = RedisUtil.getLikeKey(entityType, entityId);
		if (jedisAdopter.ismember(likeKey, String.valueOf(userId))) {
			return 1;
		}
		String disLikeKey = RedisUtil.getDisLikeKey(entityType, entityId);
		return jedisAdopter.ismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
	}

	public long like(int userId, int entityType, int entityId) {
		String likeKey = RedisUtil.getLikeKey(entityType, entityId);
		jedisAdopter.sadd(likeKey, String.valueOf(userId));

		String disLikeKey = RedisUtil.getDisLikeKey(entityType, entityId);
		jedisAdopter.srem(disLikeKey, String.valueOf(userId));

		return jedisAdopter.scard(likeKey);
	}

	public long dislike(int userId, int entityType, int entityId) {
		String disLikeKey = RedisUtil.getDisLikeKey(entityType, entityId);
		jedisAdopter.sadd(disLikeKey, String.valueOf(userId));

		String likeKey = RedisUtil.getLikeKey(entityType, entityId);
		jedisAdopter.srem(likeKey, String.valueOf(userId));

		return jedisAdopter.scard(likeKey);
	}

}
