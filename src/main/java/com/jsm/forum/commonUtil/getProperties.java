package com.jsm.forum.commonUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class getProperties {




	
    private static String name;
	
	 @SuppressWarnings("static-access")
	 @Value("${imagePath}")
	 public void setCorePoolSize(String name) {
	        this.name = name;
	}
	
	/**
	 * 获得配置文件得属性
	 * @return
	 */
	public static String getPropertiesOne(String para) {
		
		
/*		 Properties pro = new Properties();
	
		 s
		 
         try {
			//pro.load(new FileReader("src/main/resources/application.properties"));
        	 pro.load(new FileReader("application.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
      return  name;
	
	}
}
