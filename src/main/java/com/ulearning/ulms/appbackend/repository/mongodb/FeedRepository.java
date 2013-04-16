package com.ulearning.ulms.appbackend.repository.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ulearning.ulms.appbackend.entity.Feedback;

/**
 * 
 * @author heshuai
 * @version 2012-10-26
 *
 * Copyright (c) 2006-2011.Beijing WenHua Online Sci-Tech Development Co. Ltd
 * All rights reserved.
 */
public interface FeedRepository extends MongoRepository<Feedback, ObjectId>{

}
