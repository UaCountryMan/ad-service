package com.zemlyak.web.ad.creative.service;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativesDto;

import java.util.function.Function;

public class CreativeToDtoFunction implements Function<Creative, CreativesDto>{
    @Override
    public CreativesDto apply(Creative creative) {
        return new CreativesDto(
                creative.getId(),
                creative.getDescription(),
                creative.getUrl()
        );
    }
}
