package com.jsm.forum.helper;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jsm.forum.model._MappingKit;
/**
 * 启动jfinal
 * @author jsm
 *
 */
@Controller
public class initializeConfig {

	 
     @PostConstruct
	 public static void startPlugin() {
    	    
			PropKit.use("a_little_config.txt");
			DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
			ActiveRecordPlugin arp = new ActiveRecordPlugin("mysql",druidPlugin);
			_MappingKit.mapping(arp);
			
			
			druidPlugin.stop();
			arp.stop();
			
			druidPlugin.start();
			arp.start();
		}
     
    

}
