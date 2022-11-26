package com.meowsauce.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

public class SMSUtils {
    final static String secretId = "";
    final static String secretKey = "";
    final static String sdkAppId = "";
    final static String signName = "";
    final static String templateId = "";

    public static void sendMessage(String phone, String code) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(secretId, secretKey);
            // 实例化要请求产品的client对象
            SmsClient client = new SmsClient(cred, "ap-guangzhou");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName(signName);
            req.setTemplateId(templateId);
            String[] templateParamSet1 = {code};
            req.setTemplateParamSet(templateParamSet1);
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
