package com.zemlyak.web.ad.creative.dao;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativesDao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CreativesDaoCacheProxy implements CreativesDao{
    private final Map<String, List<Creative>> cache = new ConcurrentHashMap<>();
    private CreativesDao creativesDao;

    public CreativesDaoCacheProxy(CreativesDao creativesDao) {
        this.creativesDao = creativesDao;
    }

    @Override
    public List<Creative> getCreativeByOsAndCountry(String os, String country) {
        List<Creative> creativeList;
        List<Creative> creativesFromCache = getCreativeFromCache(os, country);
        if(creativesFromCache == null){
            creativeList = creativesDao.getCreativeByOsAndCountry(os, country);
            saveCreativeToCache(os, country, creativeList);
        } else {
            creativeList = creativesFromCache;
        }
        return creativeList;
    }

    public List<Creative> getCreativeFromCache(String os, String country){
        String key = getKey(os, country);
        return cache.get(key);
    }

    public void saveCreativeToCache(String os, String country, List<Creative> creativeList){
        String key = getKey(os, country);
        cache.put(key, creativeList);
    }

    private static String getKey(String os, String country){
        return os + ":" + country;
    }
}
