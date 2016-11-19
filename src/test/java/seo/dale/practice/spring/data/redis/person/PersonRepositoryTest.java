package seo.dale.practice.spring.data.redis.person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository repo;

	@Test
	public void test() {
		Person saved = new Person("rand", "al'thor");
		saved.setAddress(new Address("emond's filed", 1234));
		repo.save(saved);
		Person found = repo.findOne(saved.getId());
		System.out.println("### " + found);
		assertThat(found.getId()).isEqualTo(saved.getId());
		assertThat(repo.count()).isEqualTo(1);
		repo.delete(saved);
		assertThat(repo.count()).isEqualTo(0);
	}

	@Configuration
	@EnableRedisRepositories
	static class Config {

		@Bean
		RedisConnectionFactory connectionFactory() {
			return new JedisConnectionFactory();
		}

		@Bean
		RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
			RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
			template.setConnectionFactory(connectionFactory);
			return template;
		}

	}

}