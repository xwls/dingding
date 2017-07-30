package com.oaec.dingtalk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oaec.dingtalk.utils.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 2017/7/23.
 */
@RestController
public class APIController {
	@RequestMapping("/get_js_config")
	public String getJsConfig(@RequestParam(value = "url", required = false) String url){
		try {
			String ticket = null;
			JSONObject jsonObject = HttpHelper.httpGet(Env.URL_GET_TOKEN + "?corpid=" + Env.CORP_ID + "&corpsecret=" + Env.CORP_SECRET);
			if (jsonObject.containsKey("access_token")){
				Env.ACCESS_TOKEN = jsonObject.getString("access_token");
			}
			jsonObject = HttpHelper.httpGet(Env.URL_GET_JSAPI_TICKET+Env.ACCESS_TOKEN);
			if (jsonObject.containsKey("ticket")){
				ticket = jsonObject.getString("ticket");
			}
			String nonceStr = Utils.getRandomStr(8);
			Long  timeStamp = System.currentTimeMillis();
			String signature = DingTalkJsApiSingnature.getJsApiSignature(url, nonceStr, timeStamp, ticket);
			Map<String,Object> JsApiConfig = new HashMap<String,Object>();

			JsApiConfig.put("signature",signature);
			JsApiConfig.put("nonceStr",nonceStr);
			JsApiConfig.put("timeStamp",timeStamp);
			JsApiConfig.put("corpId",Env.CORP_ID);
			JsApiConfig.put("agentId",116121533);
			return JSON.toJSONString(JsApiConfig);
		} catch (OApiException e) {
			e.printStackTrace();
		} catch (DingTalkEncryptException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("get_user_info")
	public String getUserInfo(@RequestParam("code") String code){
		System.out.println(Env.ACCESS_TOKEN);
		try {
			JSONObject jsonObject = HttpHelper.httpGet(Env.URL_GET_USER_INFO+Env.ACCESS_TOKEN + "&code=" + code);
			if (jsonObject.containsKey("userid")){
				String userId = jsonObject.getString("userid");
				jsonObject = HttpHelper.httpGet(Env.URL_GET_USER+Env.ACCESS_TOKEN + "&userid=" + userId);
				return jsonObject.toJSONString();
			}
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return null;
	}
}
