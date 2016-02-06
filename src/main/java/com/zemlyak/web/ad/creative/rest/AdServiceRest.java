package com.zemlyak.web.ad.creative.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zemlyak.web.ad.creative.CreativesDto;
import com.zemlyak.web.ad.creative.CreativeService;

@RestController
public class AdServiceRest {
    private CreativeService creativeService;
    
    @Autowired
    public AdServiceRest(CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @RequestMapping(value="/rest/ad/get", method=RequestMethod.POST)
    public CreativesResponse getCreatives(@RequestBody CreativesRequest request){
        List<CreativesDto> dtoList = creativeService.getCreatives(request.limit, request.os, request.country);
        /*List<CreativesDto> dtoList = Arrays.asList(new CreativesDto(1,"some description","http://someDomain.ru"));*/
        return new CreativesResponse(dtoList);
    }
}
