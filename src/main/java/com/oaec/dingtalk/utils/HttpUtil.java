package com.oaec.dingtalk.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

	/**
	 * Get方式Http请求
	 * @param path 请求的url地址
	 * @param params 请求的参数
	 * @return 请求的结果
	 */
	public static String doGet(String path, String params) {
		try {
			// 1.实例化一个URL
			if (params != null) {
				path = path + "?" + params;
			}
			URL url1 = new URL(path);
			// 2.通过URL实例的openConnection()方法返回一个HttpURLConnection
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			// 3.设置请求超时的时间
			conn.setConnectTimeout(1000 * 5);
			// 4.设置请求的方式
			conn.setRequestMethod("GET");
			// 5.判断响应码是不是200
			if (conn.getResponseCode() == 200) {
				// 6.通过conn拿到一个输入流
				InputStream is = conn.getInputStream();
				// 7.把输入流转化成字节流
				InputStreamReader inputStreamReader = new InputStreamReader(is);
				// 8.把字节流转化成字符流
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				StringBuffer buffer = new StringBuffer();
				String str = null;
				// 9.循环读取字符流，每读一行赋值给一个字符串，判断字段串是否为空
				while ((str = bufferedReader.readLine()) != null) {
					// 10.循环内部把读到的每一行追加到StringBuffer
					buffer.append(str);
				}
				return buffer.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Post方式Http请求
	 * @param path 请求的url地址
	 * @param params 请求参数
	 * @return 请求结果
	 */
	public static String doPost(String path, String params) {
		try {
			// 1.创建一个URL实例
			URL url = new URL(path);
			// 2.通过URL实例的openConnection()获取HttpUrlConnection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 3.设置请求超时的时间
			conn.setReadTimeout(5000);
			// 4.设置请求方式
			conn.setRequestMethod("POST");
			// 5.把请求参数转化成byte数组
			byte[] data = params.getBytes("UTF-8");
			// 6.设置请求头信息,以表单的形式提交数据
			conn.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded;charset=utf-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			// 7.发送请求参数
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(data);
			os.flush();
			os.close();
			// 8.判断响应码是不是200
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				StringBuffer buffer = new StringBuffer();
				String str = null;
				while ((str = reader.readLine()) != null) {
					buffer.append(str);
				}
				return buffer.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}