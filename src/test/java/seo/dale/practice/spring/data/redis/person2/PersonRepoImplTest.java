package seo.dale.practice.spring.data.redis.person2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PersonRepoImplTest {

	@Autowired
	private PersonRepo repo;

	@Test
	public void test() {
		Person person = new Person("1", "Oracle", Person.Gender.Female, 55);
		Person person2 = new Person("2", "TheArchitect", Person.Gender.Male, 60);
		Person person3 = new Person("3", "TheOne", Person.Gender.Male, 25);

		repo.save(person);
		repo.save(person2);
		repo.save(person3);

		assertThat(repo.find("3").getId()).isEqualTo(person3.getId());
		assertThat(repo.findAll().size()).isEqualTo(3);

		repo.delete("2");

		assertThat(repo.find("2")).isNull();
		assertThat(repo.findAll().size()).isEqualTo(2);
	}

	@Configuration
	@ComponentScan(basePackageClasses = PersonRepoImpl.class)
	static class Config {

		@Bean
		RedisConnectionFactory connectionFactory() {
			return new JedisConnectionFactory();
		}

		@Bean
		RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
			RedisTemplate<?, ?> template = new RedisTemplate<>();
			template.setConnectionFactory(connectionFactory);
			return template;
		}

	}

}