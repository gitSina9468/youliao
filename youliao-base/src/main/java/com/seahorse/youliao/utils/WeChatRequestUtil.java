package com.seahorse.youliao.utils;

import com.seahorse.youliao.config.WeChatPayConfigs;
import com.seahorse.youliao.constant.PayTypeConstants;
import com.seahorse.youliao.entity.wechat.WeChatPayConfigEntity;
import com.seahorse.youliao.entity.wechat.WeChatQueryEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

/**
 * @description: 微信请求工具类
 * @author: Mr.Song
 * @create: 2020-03-07 19:47
 **/
public class WeChatRequestUtil {


    /**
     * 请求数据
     *
     * @return 返回结果
     */
    public static String request(String requestAddress, Object obj, String payType) {
        StringBuilder builder = new StringBuilder();
        try {
            //将实体转换为 XML 文件 WebChatQueryEntity
            String xml;
            if (payType == PayTypeConstants.WECHATQUERY) {
                xml = WeChatXmlUtil.beanToXml(WeChatQueryEntity.class, obj);
            } else {
                xml = WeChatXmlUtil.beanToXml(WeChatPayConfigEntity.class, obj);
            }

            //创建请求地址
            URL url = new URL(requestAddress);
            //获取参数
            byte[] postDataBytes = xml.getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for (int c; (c = in.read()) >= 0; ){
                builder.append((char) c);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //返回结果
        return builder.toString();
    }


    /**
     * 微信退款
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public static String refund(String url,String data) throws Exception {
        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        File certfile = ResourceUtils.getFile("classpath:cert/"+ WeChatPayConfigs.CERT_PATH);
        FileInputStream instream = new FileInputStream(certfile);
        try {
            keyStore.load(instream, WeChatPayConfigs.MCH_ID.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WeChatPayConfigs.MCH_ID.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
