package com.zemlyak.web.ad.creative;

import java.util.List;

public interface CreativeService {

    List<CreativesDto> getCreatives(int limit, String os, String country);
    List<CreativesDto> getCreativesOptimized(int limit, String os, String country);
}