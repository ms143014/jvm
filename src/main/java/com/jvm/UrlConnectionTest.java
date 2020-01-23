package com.jvm;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnectionTest {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:7910/socket/debug1/B7D57DF6954D04CC003837319F665915");
		HttpURLConnection  connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("accept", "application/json");
		connection.setDoOutput(true);
		try(OutputStream os = connection.getOutputStream()){
			os.write("sss".getBytes());
		}
		connection.connect();
		int code = connection.getResponseCode();
		System.out.println("response code:"+code);
	}
}
