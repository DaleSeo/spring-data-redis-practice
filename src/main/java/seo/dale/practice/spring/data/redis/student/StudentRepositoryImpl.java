package seo.dale.practice.spring.data.redis.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

	private static final String KEY = "Student";

	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public StudentRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void saveStudent(Student student) {
		hashOperations.put(KEY, student.getId(), student);
	}

	@Override
	public void updateStudent(Student student) {
		hashOperations.put(KEY, student.getId(), student);
	}

	@Override
	public Student findStudent(String id) {
		return (Student) hashOperations.get(KEY, id);
	}

	@Override
	public Map<Object, Object> findAllStudents() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void deleteStudent(String id) {
		hashOperations.delete(KEY, id);
	}

}
