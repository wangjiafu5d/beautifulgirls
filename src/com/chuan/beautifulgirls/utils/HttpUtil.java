package com.chuan.beautifulgirls.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.text.TextUtils;


public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		if (!TextUtils.isEmpty(address)) {
//			Log.d("TAG2", "sendHttp");
//			Log.d("TAG2", address.toString());
			new Thread(new Runnable() {

				@Override
				public void run() {
					HttpURLConnection conn = null;
					BufferedReader reader = null;
					try {						
//						Log.d("TAG", "try");
						URL url = new URL(address);
						conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(8000);
						conn.setReadTimeout(8000);
						reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gbk"));
						StringBuilder response = new StringBuilder();
						String line = "";
						
						while ((line = reader.readLine())!=null) {							
							response.append(line);
							
						}
//						Log.d("TAG3.1", ""+response.toString().length());
						if (listener != null) {
//							String temp = response.toString();
//							Log.d("TAG3.3", temp.substring(temp.length()-10, temp.length()));
//							Log.d("TAG3",temp );
							listener.onFinish(response.toString());
							
						}
					} catch (Exception e) {
						if (listener != null) {
							listener.onError(e);
						}
					} finally {
						if (conn != null) {
							conn.disconnect();
						}
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}
			}).start();
		}
	}
}
