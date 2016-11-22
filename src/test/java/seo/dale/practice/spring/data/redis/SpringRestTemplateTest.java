package seo.dale.practice.spring.data.redis;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class SpringRestTemplateTest {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	public void testForValue() {
		ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
		valueOps.set("foo", "100");
		assertThat(valueOps.get("foo")).isEqualToIgnoringCase("100");

		valueOps.increment("foo", 1);
		assertThat(valueOps.get("foo")).isEqualToIgnoringCase("101");

		BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("foo");
		boundValueOps.set("bar");
		assertThat(boundValueOps.get()).isEqualToIgnoringCase("bar");
	}

	@Test
	public void testForList() {
		ListOperations<String, String> listOps = redisTemplate.opsForList();

		listOps.leftPush("mylist", "B");
		listOps.leftPush("mylist", "A");
		listOps.rightPush("mylist", "C");

		assertThat(listOps.range("mylist", 0, -1)).isEqualTo(Arrays.asList("A", "B", "C"));

		listOps.leftPop("mylist");
		listOps.leftPop("mylist");
		listOps.leftPop("mylist");

		assertThat(listOps.range("mylist", 0, -1)).isEmpty();
	}

	@Test
	public void testForSet() {
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		setOps.add("myset", "A");
		setOps.add("myset", "B");
		setOps.add("myset", "foo");
		setOps.add("myset", "foo");
		setOps.add("myset", "bar");
		setOps.add("myset", "bar");

		assertThat(setOps.size("myset")).isEqualTo(4); // SCARD myset
		assertThat(setOps.isMember("myset", "A")).isTrue();
		assertThat(setOps.members("myset")).contains("A", "B", "foo", "bar");

		setOps.add("yourset", "B");
		setOps.add("yourset", "foo");
		setOps.add("yourset", "hello");
		assertThat(setOps.intersect("myset", "yourset")).hasSize(2);
	}

	@Test
	public void testForZSet() {
		ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
		zsetOps.add("zset", "A", 10);
		zsetOps.add("zset", "B", 15);
		zsetOps.add("zset", "B", 5);
		zsetOps.add("zset", "C", 12.55);

		assertThat(zsetOps.size("zset")).isEqualTo(3); // SCARD myset
		assertThat(zsetOps.range("zset", 0, -1)).containsExactly("B", "A", "C");
		assertThat(zsetOps.score("zset", "B")).isEqualTo(5.0);
	}

	@Test
	public void testForHash() {
		HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
		hashOps.put("myuser", "name", "Salvatore");
		hashOps.put("myuser", "surname", "Sanfilippo");
		hashOps.put("myuser", "country", "Italy");
		hashOps.put("myuser", "age", "33");

		assertThat(hashOps.get("myuser", "surname")).isEqualTo("Sanfilippo");
		assertThat(hashOps.hasKey("myuser", "address")).isFalse();

		hashOps.increment("myuser", "age", 2);
		assertThat(hashOps.get("myuser", "age")).isEqualTo("35");
	}

	@After
	public void tearDown() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
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
		StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
			StringRedisTemplate template = new StringRedisTemplate();
			template.setConnectionFactory(connectionFactory);
			return template;
		}

	}

}
