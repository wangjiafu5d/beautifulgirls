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
			new Thread(new Runnable() {

				@Override
				public void run() {
					HttpURLConnection conn = null;
					BufferedReader reader = null;
					try {
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
						if (listener != null) {
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
