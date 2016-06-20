package com.markable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private static Set<String> messageIdSet = new HashSet<String>();
    
    private static Set<Integer> amicableNumbers = new HashSet<Integer>();
    
    static {
    	System.out.println("Finding all amicable numbers");
    	int sumOfDivisors[] = new int[20000];
    	Arrays.fill(sumOfDivisors, 1);
    	for(int i=2;i<10000;i++){
    		for(int j=i;j<20000;j+=i){
    			if(j!=i)
    				sumOfDivisors[j]+=i;
    		}
    			
    	}
    	for(int i=1;i<20000;i++){
    		if(amicableNumbers.contains(i))
    			continue;
    		int s = sumOfDivisors[i];
    		if ( s<20000 && sumOfDivisors[s]==i && s!=i){
    			amicableNumbers.add(i);
    			amicableNumbers.add(s);
    		}
    			
    	}
    	System.out.println(amicableNumbers);
    }
    @RequestMapping(value="/message", method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> index(@RequestBody Map<String, String> jsonRequest) {
    	if (messageIdSet.contains(jsonRequest.get("missionId")))
    		return new ResponseEntity<String>("Conflict: Mission"+jsonRequest.get("missionId")+" already exists.", HttpStatus.CONFLICT);
    	else
    		messageIdSet.add(jsonRequest.get("missionId"));
    	Integer seed = Integer.parseInt(jsonRequest.get("seed"));
    	int sum = 0;
    	for(Integer i : amicableNumbers){
    		if (i<seed)
    			sum+=i;
    	}
        return new ResponseEntity<String>(String.valueOf(sum), HttpStatus.OK);
    }

}
