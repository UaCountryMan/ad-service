package com.zemlyak.web.ad.creative.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativeService;
import com.zemlyak.web.ad.creative.CreativesDao;
import com.zemlyak.web.ad.creative.CreativesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreativeServiceImpl implements CreativeService {
    private static final Logger LOG = LoggerFactory.getLogger(CreativeServiceImpl.class);

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
        if(LOG.isDebugEnabled()){
            LOG.debug("creativeList for os '" + os + "' and country '" + country + "' size: " + creativeList.size());
        }
        List<Creative> limitedCreativeList = reduceByLimit(limit, creativeList);
        return convertToDto(limitedCreativeList);
    }

    @Override
    public List<CreativesDto> getCreativesOptimized(int limit, String os, String country) {
        List<CreativesDto> creativeDtoList = creativesDao.getCreativeDtoByOsAndCountry(os, country);
        if(LOG.isDebugEnabled()){
            LOG.debug("creativesDtoList for os '" + os + "' and country '" + country + "' size: " + creativeDtoList.size());
        }
        return reduceByLimit(limit, creativeDtoList);
    }

    public <T> List<T> reduceByLimit(int limit, List<T> creativeList){
        if(creativeList == null || creativeList.size() <= limit){
            List<T> limitedList = creativeList!=null ? creativeList : Collections.<T>emptyList();
            return new ArrayList<>(limitedList);
        }
        
        int listSize = creativeList.size();
        List<T> limitedCreativeList = new ArrayList<>(limit);
        Set<Integer> indexSet = new HashSet<>();
        while(limitedCreativeList.size() < limit){
            int index = getUnsignedRandomInt(RANDOM) % listSize;
            if(!indexSet.add(index)){
                continue;
            }
            T creative = creativeList.get(index);
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
