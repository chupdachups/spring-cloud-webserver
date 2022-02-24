package com.example.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebserverController {
    
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("${server.port}")
	int port;

    @GetMapping("/webclient/{param}")
	public String testWebClient(
		@PathVariable String param, 
		@RequestHeader HttpHeaders headers,
		@CookieValue(name = "httpclient-type", required=false, defaultValue="undefined") String httpClientType) {
		
		log.info(">>>> Cookie 'httpclient-type={}'", httpClientType);

		headers.forEach((key, value) -> {
			log.info(String.format(">>>>> Header '%s' => %s", key, value));
		});
		
        log.info("### Received: /webclient/" + param);
		
		String msg = "("+headers.get("host")+":"+port+")"+param + " => Working successfully !!! \n";
		log.info("### Sent: " + msg);
		return msg;
	}
    
    @GetMapping("/testSCCB/{param}")
	public String testSCCB(
		@PathVariable String param, 
		@RequestHeader HttpHeaders headers,
		@CookieValue(name = "httpclient-type", required=false, defaultValue="undefined") String httpClientType) throws Exception {
		
		log.info(">>>> Cookie 'httpclient-type={}'", httpClientType);

		headers.forEach((key, value) -> {
			log.info(String.format(">>>>> Header '%s' => %s", key, value));
		});
		
        log.info("### Received: /testSCCB/" + param);
		
		String msg = "("+headers.get("host")+":"+port+")"+param + " => Working successfully !!!";
		log.info("### Sent: " + msg);
		
		if(param.equals("test")) {
			throw  new RuntimeException("Force Error");
		}
		return msg;
	}

}