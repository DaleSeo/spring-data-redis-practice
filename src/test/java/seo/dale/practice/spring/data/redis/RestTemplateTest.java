package seo.dale.practice.spring.data.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RestTemplateTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void test() {
		System.out.println("READY");
	}

	@Configuration
	static class Config {

		@Bean
		RedisConnectionFactory connectionFactory() {
			JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
			jedisConnectionFactory.setHostName("localhost");
			jedisConnectionFactory.setPort(6379);
			return jedisConnectionFactory;
		}

		@Bean
		RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
			RedisTemplate<String, Object> template = new RedisTemplate<>();
			template.setConnectionFactory(connectionFactory);
			return template;
		}

	}

}
