package seo.dale.practice.spring.data.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class RestTemplateTest {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	public void test() {
		ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
		valueOps.set("key1", "val1");
		String value = valueOps.get("key1");
		assertThat(value).isEqualToIgnoringCase("val1");
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
		RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
			RedisTemplate<?, ?> template = new RedisTemplate<>();
			template.setConnectionFactory(connectionFactory);
			return template;
		}

	}

}
