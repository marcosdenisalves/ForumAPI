package br.com.alura.forum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.lang.Assert;

@SpringBootTest
class ForumApplicationTests {

	@Test
	void contextLoads() {
		Assert.isAssignable(getClass(), getClass());
	}
}
