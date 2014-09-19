package com.liubaing.galaxy.repository.mongodb;

import com.liubaing.galaxy.entity.Platform;
import com.liubaing.galaxy.entity.Version;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author heshuai
 * @version 2012-10-26
 *
 */
public interface VersionRepository extends MongoRepository<Version, ObjectId>{

	Version findByPlatformOrderByVersionCodeDesc(Platform platform);
	
}
