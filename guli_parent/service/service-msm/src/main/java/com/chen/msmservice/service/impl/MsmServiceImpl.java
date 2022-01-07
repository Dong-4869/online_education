package com.chen.msmservice.service.impl;

import com.chen.msmservice.service.MsmService;
import com.chen.msmservice.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String appcode = "a0ce2aed6afd4f4ab84bbae3350603ff";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", phone);
        querys.put("param", "code:"+code);
        querys.put("tpl_id", "TP1711063");
        Map<String, String> bodys = new HashMap<>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
