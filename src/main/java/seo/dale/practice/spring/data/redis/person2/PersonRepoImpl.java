package seo.dale.practice.spring.data.redis.person2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class PersonRepoImpl implements PersonRepo {

	private static String PERSON_KEY = "Person";

	private RedisTemplate<String, String> restTemplate;

	@Autowired
	public PersonRepoImpl(RedisTemplate<String, String> restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void save(Person person) {
		restTemplate.opsForHash().put(PERSON_KEY, person.getId(), person);
	}

	@Override
	public Person find(String id) {
		return (Person) restTemplate.opsForHash().get(PERSON_KEY, id);
	}

	@Override
	public Map<Object, Object> findAll() {
		return restTemplate.opsForHash().entries(PERSON_KEY);
	}

	@Override
	public void delete(String id) {
		restTemplate.opsForHash().delete(PERSON_KEY, id);
	}

}
