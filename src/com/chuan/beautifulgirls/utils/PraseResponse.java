package com.chuan.beautifulgirls.utils;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PraseResponse {
	
	public synchronized static void handleResponse(String response,List<String> list,Map<String,List<String>> map){
				
		//����������ҳ��Դ����һ����ȡ��������<start>����</end>֮�䣬�ڶ�����ȡͼƬ���࣬<element>����</element>֮�䣬
		//����������ȡͼƬurl�б�����list��map
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
		MyApplication.urlUpdateFlag = true;//����url��ȡ��ʶ��Ϊ��ȡ����
	}
	
}
