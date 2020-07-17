package com.baizhi.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

public class SendSms {

    private static void sendPhoneCode(String phones, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fxxay2DkJCdDe2NkjT1", "s9KJ7QCG2GnnfbAPbCcpiVgdIgfTU9");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phones);
        request.putQueryParameter("SignName", "新丽洗浴");
        request.putQueryParameter("TemplateCode", "SMS_195575792");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成相应长度的数字随机数
     *
     * @param size 长度
     * @return String
     */
    public static String getRandCode(int size) {
        String temp = "1234567890";
        int length = temp.length();
        int p;
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            p = r.nextInt(length);
            sb.append(temp.substring(p, p + 1));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String phones = "15566286121,17719807219,13347080578,13347080578";
        String randCode = getRandCode(6);
        System.out.println(randCode);
        sendPhoneCode(phones, randCode);
    }
}
