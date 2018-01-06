package com.netbug_nb.uc.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

public class BaseMongoRepositoryImpl<T, ID extends Serializable> implements BaseMongoRepository<T, ID> {

	@Autowired
	private MongoOperations mongoOperations;

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseMongoRepositoryImpl() {
		// 当前对象的直接超类的 Type
		Type genericSuperclass = getClass().getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			// 参数化类型
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			// 返回表示此类型实际类型参数的 Type 对象的数组
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			this.clazz = (Class<T>) actualTypeArguments[0];
		} else {
			this.clazz = (Class<T>) genericSuperclass;
		}
	}

	public Class<T> getClazz() {
		return clazz;
	}

	@Override
	public long count(Query query) {
		return mongoOperations.count(query, this.getClazz());
	}

}
