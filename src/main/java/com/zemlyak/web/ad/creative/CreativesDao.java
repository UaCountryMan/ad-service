package com.zemlyak.web.ad.creative;

import java.util.List;

public interface CreativesDao {
    List<Creative> getCreativeByOsAndCountry(String os, String country);
    List<CreativesDto> getCreativeDtoByOsAndCountry(String os, String country);
}