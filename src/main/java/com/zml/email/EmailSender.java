package com.zml.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.zml.entity.Movie;

public class EmailSender {

	private static String convert(List<Movie> dataList) {
		JSONObject ret = new JSONObject();

		JSONArray to = new JSONArray();
		JSONArray names = new JSONArray();
		JSONArray releaseTimes = new JSONArray();
		JSONArray actors = new JSONArray();
		for (Movie m : dataList) {
			to.put("461519240@qq.com");
			names.put(m.getName());
			releaseTimes.put(m.getReleaseTime());
			actors.put(m.getActors());
		}

		JSONObject sub = new JSONObject();
		sub.put("%name%", names);
		sub.put("%money%", releaseTimes);
		sub.put("%actors%", actors);
		
		ret.put("to", to);
		ret.put("sub", sub);

		return ret.toString();
	}

	public static void sendEmailByTemplate(List<Movie> movies) throws ClientProtocolException, IOException {
		if (movies.size() == 0) {
			return;
		}
		
		final String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";
		final String apiUser = "aidanzhang_test_vBgJeV";
	    final String apiKey = "zfilHjqFNx4WQ8ZM";
	
		String subject = "Movie upcoming reminder!";
	
		final String vars = convert(movies);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_user", apiUser));
		params.add(new BasicNameValuePair("api_key", apiKey));
		params.add(new BasicNameValuePair("substitution_vars", vars));
		params.add(new BasicNameValuePair("template_invoke_name", "new_movie_reminder"));
		params.add(new BasicNameValuePair("from", "aidan@74dRO9VrbHZfbcz1b9Yp5qtZ7xP3AbEr.sendcloud.org"));
		params.add(new BasicNameValuePair("fromname", "aidan"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("resp_email_id", "true"));
	
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	
		HttpResponse response = httpClient.execute(httpPost);
	
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}
}
