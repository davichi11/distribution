package com.distribution.weixin.wxpay.utils;

import com.distribution.common.utils.DateUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WxpayUtil {


    public static String getip() throws UnknownHostException {
        InetAddress ia = null;

        ia = InetAddress.getLocalHost();

        String localname = ia.getHostName();
        String localip = ia.getHostAddress();
        System.out.println("本机名称是：" + localname);
        System.out.println("本机的ip是 ：" + localip);
        return localip;
    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getNonceStr() {
        // 随机数
        String currTime = DateUtils.formatDateTime(LocalDateTime.now(), "yyyyMMddHHmmss");
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        return strTime + strRandom;
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map;

        // 从request中取得输入流
        try (InputStream inputStream = request.getInputStream()) {
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            map = elementList.stream().collect(Collectors.toMap(Node::getName, Element::getText, (a, b) -> b));
        }

        return map;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @Override
                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * 生成二维码图片 不存储 直接以流的形式输出到页面
     *
     * @param content
     * @param response
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void encodeQrcode(String content, HttpServletResponse response) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        // 设置字符集编码类型
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            // 输出二维码图片流
            try {
                ImageIO.write(image, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private static BufferedImage toBufferedImage(BitMatrix matrix) {

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);

            }
        }
        return image;

    }

    public static boolean verify(Map<String, String> params, String key) {
        // 过滤空值、sign与sign_type参数
        SortedMap<String, String> sParaNew = paraFilter(params);
        System.out.println("SortedMap:" + sParaNew.toString());
        // 获得签名结果
        String mysign = createSign(sParaNew, key);
        System.out.println("mysign:" + mysign);
        String sign = params.getOrDefault("sign", "");
        return mysign.equals(sign);
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static SortedMap<String, String> paraFilter(Map<String, String> sArray) {

        SortedMap<String, String> result = new TreeMap<>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        sArray.forEach((key, value) -> {
            if (StringUtils.isNotBlank(value) || "sign".equalsIgnoreCase(key) || "sign_type".equalsIgnoreCase(key)) {
                return;
            }
            result.put(key, value);

        });
        return result;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    private static String createSign(SortedMap<String, String> packageParams, String key) {
        StringBuilder sb = new StringBuilder();
        packageParams.forEach((k, v) -> {
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        });
        sb.append("key=").append(key);
        System.out.println("sb:" + sb.toString());
        return MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();

    }

}
