package seo.dale.practice.spring.data.redis.student;

import java.util.Map;

public interface StudentRepository {

	void saveStudent(Student student);

	void updateStudent(Student student);

	Student findStudent(String id);

	Map<Object, Object> findAllStudents();

	void deleteStudent(String id);

}
