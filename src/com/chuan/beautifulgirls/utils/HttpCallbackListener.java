package com.chuan.beautifulgirls.utils;

public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
