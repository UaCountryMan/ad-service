package com.zemlyak.web.ad.creative.rest;

import java.util.List;

import com.zemlyak.web.ad.creative.CreativesDto;

public class CreativesResponse {
    public List<CreativesDto> ads;

    public CreativesResponse(){}
    
    public CreativesResponse(List<CreativesDto> ads) {
        this.ads = ads;
    }
}