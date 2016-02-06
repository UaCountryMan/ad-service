package com.zemlyak.web.ad.creative;

import java.util.List;

import com.zemlyak.web.ad.creative.CreativesDto;

public interface CreativeService {

    List<CreativesDto> getCreatives(int limit, String os, String country);

}