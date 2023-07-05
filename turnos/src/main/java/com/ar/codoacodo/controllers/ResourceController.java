package com.ar.codoacodo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.codoacodo.dto.reqers.ListResource;
import com.ar.codoacodo.service.feign.FeignResourceService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {

	@Value(value = "${ENDPOINT_REQ_RES}")
	private String apiEndpoint;
	
	@GetMapping()
	public ResponseEntity<ListResource> findAll() {
		
		/*RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<ListResource> response = restTemplate.getForEntity(apiEndpoint, ListResource.class);
		
		return ResponseEntity.ok(response.getBody());
		*/
		
		FeignResourceService response  = Feign.builder()
				  .client(new OkHttpClient())
				  .encoder(new GsonEncoder())
				  .decoder(new GsonDecoder())
				  .logger(new Slf4jLogger(FeignResourceService.class))
				  .logLevel(Logger.Level.FULL)
				  .target(FeignResourceService.class, apiEndpoint);
		
		//save en db
		
		return ResponseEntity.ok(response.findAll());
	}
}
