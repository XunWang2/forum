package com.jsm.forum.commonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PubJfinal {
	
	public static Map<String,Object> paseMap(Page pag){
		Map<String,Object> pageList = new HashMap<String,Object>();
		List list = pag.getList();
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>() ;
		
		if(list!=null&&list.size()>0 ) {
			if(list.get(0).getClass().getName().equals(Record.class.getName())){
				for (int i = 0; i < list.size(); i++) {
					listMap.add( ((Record)list.get(i)).getColumns() );
				}
				pageList.put("rows",listMap) ;
			}else{
				pageList.put("rows",list) ;
			}
		}else{
			pageList.put("rows",list) ;
		}
		pageList.put("total",pag.getTotalRow()) ;
		return pageList ; 
	}

}
