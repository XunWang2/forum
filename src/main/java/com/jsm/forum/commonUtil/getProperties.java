package com.jsm.forum.commonUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class getProperties {

	/**
	 * 获得配置文件得属性
	 * @return
	 */
	public static String getPropertiesOne(String para) {
		 Properties pro = new Properties();
         try {
			pro.load(new FileReader("src/main/resources/application.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      return  pro.getProperty(para);
	
	}
}
