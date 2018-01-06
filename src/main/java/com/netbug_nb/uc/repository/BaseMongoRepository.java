package com.netbug_nb.uc.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.core.query.Query;

public interface BaseMongoRepository<T, ID extends Serializable> {
	public long count(Query query);
}
