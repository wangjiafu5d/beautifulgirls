package com.chuan.beautifulgirls.utils;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PraseResponse {
	
	public synchronized static void handleResponse(String response,List<String> list,Map<String,List<String>> map){
				
		//三步解析网页资源，第一步获取正文内容<start>——</end>之间，第二步获取图片子类，<element>——</element>之间，
		//第三步，获取图片url列表，加入list和map
		Pattern p = Pattern.compile("&lt;start&gt[\\s\\S]{5,}?&lt;/end&gt");
		Matcher m = p.matcher(response);
		while (m.find()) {
			Pattern p2 = Pattern.compile("&lt;element&gt[\\s\\S]{15,}?&lt;/element&gt");
			Matcher m2 = p2.matcher(m.group());
			while (m2.find()) {
				Pattern p3 = Pattern.compile("http://[\\s\\S]{15,}?.jpg");
				Matcher m3 = p3.matcher(m2.group());
				List<String> child_list = new ArrayList<String>();
				while (m3.find()) {
					child_list.add(m3.group());	
				}				
				list.add(child_list.get(0));
				map.put(child_list.get(0), child_list);
			}
		}
		MyApplication.urlUpdateFlag = true;//设置url获取标识符为获取到了
	}
	
}
