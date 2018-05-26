package com.distribution.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 短信发送消费
 *
 * @author ChunLiang Hu
 * @Company
 * @Project jsdemo
 * @Package com.gxzb.jsdemo.core.queue
 * @Description TODO(描述)
 * @create 2018/1/17-14:13
 */
@Slf4j
@Component
@RabbitListener(queues = "notify")
public class NotifyReceiver {

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String ACCESS_KEY_ID = "LTAINPI8FPggKcPG";
    private static final String ACCESS_KEY_SECRET = "zrQmH9MvijTVOWSxYRsHvztGhDgZ7M";

    @SuppressWarnings("unchecked")
    @RabbitHandler
    public void process(String msg) {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        JSONObject json = JSON.parseObject(msg);
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(json.getString("cellphone"));
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("道手网络");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_135480064");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(msg);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (!"OK".equals(sendSmsResponse.getCode())) {
                log.error(MessageFormat.format("手机号{0}的用户短信发送失败,+Message={1}", json.getString("cellphone"), sendSmsResponse.getMessage()));
            }
        } catch (ClientException e) {
            log.error("短信发送异常");
        }
    }
}
