package com.markable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RequestScheduler {

	@Scheduled(initialDelay=3000, fixedRate=3000)
	public void eulerProblemSeedGenerator(){
		
		Random r = new Random();
		int missionId = r.nextInt(900)+100;
		r= new Random();
		int seed = r.nextInt(1900)*10+1000;
		Map<String, String> requestJsonMap = new HashMap<>();
		requestJsonMap.put("missionId",String.valueOf(missionId));
		requestJsonMap.put("seed", String.valueOf(seed));
		
		
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost("http://localhost:8080/message");
            String reqJson = new ObjectMapper().writeValueAsString(requestJsonMap);
            System.out.println("Sending request - "+reqJson);
            
            StringEntity params =new StringEntity(reqJson);
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept","application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity= response.getEntity();
            String output = entity != null ? EntityUtils.toString(entity) : null;
            System.out.println(response.getStatusLine()+" - "+ output);
 
        }catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            
        }
		
	}
}
