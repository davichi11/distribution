package com.distribution.weixin.wxpay.utils;


import com.distribution.weixin.wxpay.utils.http.HttpClientConnectionManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetWxOrderno {
    private static DefaultHttpClient httpclient;

    static {
        httpclient = new DefaultHttpClient();
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
    }


    /**
     * description:获取预支付id
     *
     * @param url
     * @param xmlParam
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    public static String getPayNo(String url, String xmlParam) {
//	  DefaultHttpClient client = new DefaultHttpClient();
//	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String prepayId = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(jsonStr);
            if (jsonStr.contains("FAIL")) {
                return prepayId;
            }
            Map map = doXMLParse(jsonStr);
            prepayId = (String) map.get("prepay_id");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return prepayId;
    }

    /**
     * description:获取扫码支付连接
     *
     * @param url
     * @param xmlParam
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    public static String getCodeUrl(String url, String xmlParam) {
//	  DefaultHttpClient client = new DefaultHttpClient();
//	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String codeUrl = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(jsonStr);
            if (jsonStr.contains("FAIL")) {
                return codeUrl;
            }
            Map map = doXMLParse(jsonStr);
            codeUrl = (String) map.get("code_url");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return codeUrl;
    }


    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    private static Map doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map<String,Object> m = new HashMap<>();
        try (InputStream in = string2Inputstream(strxml)) {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            for (Object aList : list) {
                Element e = (Element) aList;
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = getChildrenText(children);
                }

                m.put(k, v);
            }
        }

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            for (Object aChildren : children) {
                Element e = (Element) aChildren;
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }

        return sb.toString();
    }

    private static InputStream string2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * wyg
     * 微信获取对账数据
     */
    public static String getWxMap(String url, String xmlParam) {
//	  Map<String, String> map=null;
        String jsonStr = null;
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (jsonStr.contains("FAIL")) {
                return "tw" + jsonStr;
            }
            System.out.println(jsonStr);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}