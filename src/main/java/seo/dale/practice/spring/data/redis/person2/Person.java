package seo.dale.practice.spring.data.redis.person2;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -8243145429438016231L;

	public enum Gender {Male, Female}

	private String id;

	private String name;

	private Gender gender;

	private int age;

	public Person() {
	}

	public Person(String id, String name, Gender gender, int age) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		if (age != person.age) return false;
		if (id != null ? !id.equals(person.id) : person.id != null) return false;
		if (name != null ? !name.equals(person.name) : person.name != null) return false;
		return gender == person.gender;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (gender != null ? gender.hashCode() : 0);
		result = 31 * result + age;
		return result;
	}

	@Override
	public String toString() {
		return "Person{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", gender=" + gender +
				", age=" + age +
				'}';
	}

}
