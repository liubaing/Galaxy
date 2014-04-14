package com.liubaing.galaxy.repository.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.liubaing.galaxy.entity.Feedback;

/**
 * 
 * @author heshuai
 * @version 2012-10-26
 *
 */
public interface FeedRepository extends MongoRepository<Feedback, ObjectId>{

}
