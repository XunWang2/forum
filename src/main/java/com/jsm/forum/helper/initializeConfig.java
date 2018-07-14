package com.jsm.forum.helper;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jsm.forum.model._MappingKit;
/**
 * 启动jfinal
 * @author jsm
 *
 */
@Component
public class initializeConfig {

	 
     @PostConstruct
	 public static void startPlugin() {
    	    
			PropKit.use("application.properties");
			DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("spring.datasource.url"), PropKit.get("spring.datasource.username"), PropKit.get("spring.datasource.password").trim());
			ActiveRecordPlugin arp = new ActiveRecordPlugin("mysql",druidPlugin);
			_MappingKit.mapping(arp);
			
			
			druidPlugin.stop();
			arp.stop();
			
			druidPlugin.start();
			arp.start();
		}
     
    

}
