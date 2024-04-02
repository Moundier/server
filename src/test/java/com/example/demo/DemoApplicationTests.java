package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	String contextLoads() {
		String str = "Hello WOrld".toLowerCase().replace(" ", "-");  
		System.out.println(str);
		
		return str;
	}

}
