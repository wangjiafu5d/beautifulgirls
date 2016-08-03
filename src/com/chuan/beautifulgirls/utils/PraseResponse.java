package com.chuan.beautifulgirls.utils;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class PraseResponse {
	
	public synchronized static void handleResponse(String response){
//		Log.d("TAG4", response);
//		File file = new File(Environment.getExternalStorageDirectory(), "response.txt");
//		try {
//			FileOutputStream fo = new FileOutputStream(file);			
//			fo.write(response.getBytes());
//			fo.flush();
//			fo.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		Pattern p = Pattern.compile("&lt;start&gt[\\s\\S]{5,}?&lt;/end&gt");
//		
		Matcher m = p.matcher(response);
//		String str = "";
		while (m.find()) {
			Pattern p2 = Pattern.compile("&lt;element&gt[\\s\\S]{5,}?&lt;/element&gt");
			Matcher m2 = p2.matcher(m.group());
			while (m2.find()) {
				Pattern p3 = Pattern.compile("http://[\\s\\S]{5,}?.jpg");
				Matcher m3 = p3.matcher(m2.group());
				List<String> child_list = new ArrayList<String>();
				while (m3.find()) {
					child_list.add(m3.group());	
//					str = str +"\n"+m3.group();
				}
				MyApplication.addUrlList(child_list.get(0));
				MyApplication.addUrlMap(child_list.get(0), child_list);
			}
		}
		
//		Log.d("TAG", str);
//		return str;
			
	}
	
}
