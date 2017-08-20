package com.zml.reminder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Hello world!
 *
 */
public class App 
{//694270731@qq.com;277896128@qq.com
	public static void main( String[] args ) throws Exception {
	      final String url = "http://api.sendcloud.net/apiv2/mail/send";
	         final String apiUser = "aidanzhang_test_vBgJeV";
	         final String apiKey = "zfilHjqFNx4WQ8ZM";

	         CloseableHttpClient httpclient = HttpClients.createDefault();
	         HttpPost httPost = new HttpPost(url);

	         List<BasicNameValuePair> params = new ArrayList<>();
	         // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
	         params.add(new BasicNameValuePair("apiUser", apiUser));
	         params.add(new BasicNameValuePair("apiKey", apiKey));
	         params.add(new BasicNameValuePair("from", "aidanzhang@74dRO9VrbHZfbcz1b9Yp5qtZ7xP3AbEr.sendcloud.org"));
	         params.add(new BasicNameValuePair("fromName", "aaa"));
	         params.add(new BasicNameValuePair("to", "461519240@qq.com"));
	         params.add(new BasicNameValuePair("subject", "Aidan First Email Test"));
	         params.add(new BasicNameValuePair("html", "Rafael Song and Stan luo are good friends"));

	         httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	         // 请求
	         CloseableHttpResponse response = httpclient.execute(httPost);

	         // 处理响应
	         if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
	           // 读取xml文档
	           String result = EntityUtils.toString(response.getEntity());
	           System.out.println(result);
	         } else {
	         System.out.println(response.getStatusLine().getStatusCode());
	            System.err.println("error");
	         }
	         httPost.releaseConnection();
	    }
}
