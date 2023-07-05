package com.ar.codoacodo.service.feign;



import com.ar.codoacodo.dto.reqers.ListResource;

import feign.RequestLine;

public interface FeignResourceService {
	@RequestLine("GET")
	ListResource findAll();
}