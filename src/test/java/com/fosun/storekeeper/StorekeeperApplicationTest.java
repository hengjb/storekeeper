package com.fosun.storekeeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * test
 * @author hengjb
 * @date 2019/08/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorekeeperApplicationTest {

	@Test
	public void contextLoads() {
	}
	
	public static void main(String[] args) {
	    String name = "test.txt";
        String ext = name.substring(name.lastIndexOf(".") + 1);
        System.out.println(ext);
        System.out.println(name.substring(0, name.lastIndexOf(".")));
    }

}
