package com.liubaing.galaxy.repository.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.liubaing.galaxy.entity.Platform;
import com.liubaing.galaxy.entity.Version;

/**
 * 
 * @author heshuai
 * @version 2012-10-26
 *
 */
public interface VersionRepository extends MongoRepository<Version, ObjectId>{

	public Version findByPlatformOrderByVersionCodeDesc(Platform platform);
	
}
