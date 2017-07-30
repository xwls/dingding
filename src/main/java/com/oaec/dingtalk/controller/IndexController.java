package com.oaec.dingtalk.controller;

import com.oaec.dingtalk.utils.Env;
import com.oaec.dingtalk.utils.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kevin on 2017/7/23.
 */
@Controller
public class IndexController {
	@RequestMapping("/")
	public String index(Model model){
		String token = HttpUtil.doGet(Env.URL_GET_TOKEN, "corpid=" + Env.CORP_ID + "&corpsecret=" + Env.CORP_SECRET);
		String jsapi_ticket = HttpUtil.doGet(Env.URL_GET_JSAPI_TICKET, "access_token=" + token);
		model.addAttribute("token",token);

		return "index";
	}
}
