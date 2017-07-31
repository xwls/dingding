package com.oaec.dingtalk.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.oaec.dingtalk.utils.Env;
import com.oaec.dingtalk.utils.HttpHelper;
import com.oaec.dingtalk.utils.OApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by Kevin on 2017/7/31.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
	@RequestMapping("index")
	public String index(@RequestParam(value = "code",required = false) String code, HttpSession session){
		Object user_info = session.getAttribute("user_info");
		if (user_info == null){
			sso(code,session);
		}
		System.out.println(user_info);;
		return "admin/index";
	}

	private void sso(String code, HttpSession session){
		try {
			JSONObject jsonObject = HttpHelper.httpGet(Env.URL_GET_SSO_TOKEN);
			if (jsonObject.containsKey("access_token")){
				String access_token = jsonObject.getString("access_token");
				jsonObject = HttpHelper.httpGet(Env.URL_GET_SSO_USER + access_token + "&code=" + code);
				if (jsonObject.getInteger("errcode") == 0){
					JSONObject user_info = jsonObject.getJSONObject("user_info");
					session.setAttribute("user_info",user_info);
				}
			}
		} catch (OApiException e) {
			e.printStackTrace();
		}
	}
}
