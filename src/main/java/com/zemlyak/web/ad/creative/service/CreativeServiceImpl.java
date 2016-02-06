package com.zemlyak.web.ad.creative.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativeService;
import com.zemlyak.web.ad.creative.CreativesDao;
import com.zemlyak.web.ad.creative.CreativesDto;

public class CreativeServiceImpl implements CreativeService {
    private static final Random RANDOM = new Random();
    private static final Function<Creative, CreativesDto> creativeToDtoFunction = new CreativeToDtoFunction();
    
    private CreativesDao creativesDao;

    public CreativeServiceImpl(CreativesDao creativesDao) {
        this.creativesDao = creativesDao;
    }
    
    /* (non-Javadoc)
     * @see com.zemlyak.web.ad.creative.CreativeService#getCreatives(int, java.lang.String, java.lang.String)
     */
    @Override
    public List<CreativesDto> getCreatives(int limit, String os, String country){
        List<Creative> creativeList = creativesDao.getCreativeByOsAndCountry(os, country);
        
        System.out.println("creativeList size: " + creativeList.size());
        
        List<Creative> limitedCreativeList = reduceByLimit(limit, creativeList);
        return convertToDto(limitedCreativeList);
    }

    public List<Creative> reduceByLimit(int limit, List<Creative> creativeList){
        if(creativeList == null || creativeList.size() <= limit){
            List<Creative> limitedList = creativeList!=null ? creativeList : Collections.<Creative>emptyList();
            return new ArrayList<>(limitedList);
        }
        
        int listSize = creativeList.size();
        List<Creative> limitedCreativeList = new ArrayList<>(limit);
        for(int i = 0; i < limit; i++){
            int index = getUnsignedRandomInt(RANDOM) % listSize;
            Creative creative = creativeList.get(index);
            limitedCreativeList.add(creative);
        }
        return limitedCreativeList;
    }
    
    public List<CreativesDto> convertToDto(List<Creative> limitedCreativeList) {
        return limitedCreativeList
            .stream()
            .map(creativeToDtoFunction)
            .collect(Collectors.toList());
    }
    
    private static int getUnsignedRandomInt(Random random){
        int value;
        do{
            value = Math.abs(random.nextInt());
        } while(value == Integer.MIN_VALUE);
        return value;
    }
}
