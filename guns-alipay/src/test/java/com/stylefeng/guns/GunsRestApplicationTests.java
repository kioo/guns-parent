package com.stylefeng.guns;

import com.stylefeng.guns.rest.AlipayApplication;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring boot 配置测试
 */
@RunWith(SpringRunner.class) // 1. 必须使用
@SpringBootTest(classes = AlipayApplication.class) // 2. classes写上该项目启动类的class文件
public class GunsRestApplicationTests {

	@Autowired // 3. 注入要测试的类
	private FTPUtil ftpUtil;

	@Test
	public void contextLoads() {
		String fileStrByAddress = ftpUtil.getFileStrByAddress("nihao/jianbiao.json");
		System.out.println(fileStrByAddress);
	}

}
