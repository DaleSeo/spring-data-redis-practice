package seo.dale.practice.spring.data.redis.student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class StudentRepositoryImplTest {

	@Autowired
	private StudentRepositoryImpl repository;

	@Test
	public void whenSavingStudent_thenAvailableOnRetrieval() {
		Student saved = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
		repository.saveStudent(saved);
		Student found = repository.findStudent(saved.getId());
//		System.out.println("### " + found);
		assertThat(found.getId()).isEqualTo(saved.getId());
	}

	@Test
	public void whenUpdatingStudent_thenAvailableOnRetrieval() {
		Student saved = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
		repository.saveStudent(saved);
		saved.setName("Richard Watson");
		repository.saveStudent(saved);
		Student found = repository.findStudent(saved.getId());
		assertThat(found.getName()).isEqualTo(saved.getName());
	}

	@Test
	public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() {
		Student engStudent = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
		Student medStudent = new Student("Med2015001", "Gareth Houston", Student.Gender.MALE, 2);

		repository.saveStudent(engStudent);
		repository.saveStudent(medStudent);

		Map<Object, Object> students = repository.findAllStudents();
//		System.out.println("### " + students);
		assertThat(students).size().isEqualTo(2);
	}

	@Test
	public void whenDeletingStudent_thenNotAvailableOnRetrieval() {
		Student saved = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
		repository.saveStudent(saved);
		repository.deleteStudent(saved.getId());
		Student found = repository.findStudent(saved.getId());
		assertThat(found).isNull();
	}

}