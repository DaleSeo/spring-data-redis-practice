package seo.dale.practice.spring.data.redis.person2;

import java.util.Map;

public interface PersonRepo {

	void save(Person person);
	Person find(String id);
	Map<Object, Object> findAll();
	void delete(String id);

}
