package com.zemlyak.web.ad.creative.service;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativesDao;
import com.zemlyak.web.ad.creative.CreativesDto;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class CreativeServiceImplTest {
    @Mock
    private CreativesDao creativesDaoMock;

    @Test
    public void convertToDtoTest(){
        Integer testId = 1;
        String testUrl = "testUrl";
        String testDescription = "testDescription";
        List<Creative> creativeListForReturnConvert = Arrays.asList(new Creative(testId, testDescription, testUrl, null, null, null, null));

        replay(creativesDaoMock);

        CreativeServiceImpl creativeService = new CreativeServiceImpl(creativesDaoMock);
        List<CreativesDto> creativesDtoList = creativeService.convertToDto(creativeListForReturnConvert);

        assertEquals(creativeListForReturnConvert.size(), creativesDtoList.size());
        Creative creative = creativeListForReturnConvert.iterator().next();
        CreativesDto creativesDto = creativesDtoList.iterator().next();
        assertEquals(creative.getId(), creativesDto.id);
        assertEquals(creative.getUrl(), creativesDto.url);
        assertEquals(creative.getDescription(), creativesDto.description);
    }

    @Test
    public void reduceByLimitTest(){
        int limit = 2;

        List<Creative> oneElementsList = Arrays.asList(
                new Creative(1, "testDescription", "testUrl", null, null, null, null));

        List<Creative> twoElementsList = Arrays.asList(
                new Creative(1, "testDescription", "testUrl", null, null, null, null),
                new Creative(2, "testDescription2", "testUrl2", null, null, null, null));

        List<Creative> threeElementsList = Arrays.asList(
                new Creative(1, "testDescription", "testUrl", null, null, null, null),
                new Creative(2, "testDescription2", "testUrl2", null, null, null, null),
                new Creative(3, "testDescription3", "testUrl3", null, null, null, null));

        replay(creativesDaoMock);

        CreativeServiceImpl creativeService = new CreativeServiceImpl(creativesDaoMock);
        List<Creative> oneElementsReducedList = creativeService.reduceByLimit(limit, oneElementsList);
        List<Creative> twoElementsReducedList = creativeService.reduceByLimit(limit, twoElementsList);
        List<Creative> threeElementsReducedList = creativeService.reduceByLimit(limit, threeElementsList);

        assertEquals(oneElementsList.size(), oneElementsReducedList.size());
        assertEquals(twoElementsList.size(), twoElementsReducedList.size());
        assertEquals(limit, threeElementsReducedList.size());
    }

    @Test
    public void getCreativesTest(){
        int limit = 2;
        String os = "someOs";
        String country = "someCountry";

        List<Creative> oneElementsList = Arrays.asList(
                new Creative(1, "testDescription", "testUrl", null, null, null, null));

        List<Creative> twoElementsList = Arrays.asList(
                new Creative(1, "testDescription", "testUrl", null, null, null, null),
                new Creative(2, "testDescription2", "testUrl2", null, null, null, null));

        List<Creative> threeElementsList = Arrays.asList(
                new Creative(1, "testDescription", "testUrl", null, null, null, null),
                new Creative(2, "testDescription2", "testUrl2", null, null, null, null),
                new Creative(3, "testDescription3", "testUrl3", null, null, null, null));

        expect(creativesDaoMock.getCreativeByOsAndCountry(os,country))
                .andReturn(oneElementsList)
                .andReturn(twoElementsList)
                .andReturn(threeElementsList);
        replay(creativesDaoMock);

        CreativeServiceImpl creativeService = new CreativeServiceImpl(creativesDaoMock);
        List<CreativesDto> oneCreativesDtoList = creativeService.getCreatives(limit, os, country);
        List<CreativesDto> twoCreativesDtoList = creativeService.getCreatives(limit, os, country);
        List<CreativesDto> limitedCreativesDtoList = creativeService.getCreatives(limit, os, country);

        assertEquals(oneElementsList.size(), oneCreativesDtoList.size());
        assertEquals(twoElementsList.size(), twoCreativesDtoList.size());
        assertEquals(limit, limitedCreativesDtoList.size());
    }

    @Test
    public void getCreativesOptimizedTest(){
        int limit = 2;
        String os = "someOs";
        String country = "someCountry";

        List<CreativesDto> oneElementsList = Arrays.asList(
                new CreativesDto(1, "testDescription", "testUrl"));

        List<CreativesDto> twoElementsList = Arrays.asList(
                new CreativesDto(1, "testDescription", "testUrl"),
                new CreativesDto(2, "testDescription2", "testUrl2"));

        List<CreativesDto> threeElementsList = Arrays.asList(
                new CreativesDto(1, "testDescription", "testUrl"),
                new CreativesDto(2, "testDescription2", "testUrl2"),
                new CreativesDto(3, "testDescription3", "testUrl3"));

        expect(creativesDaoMock.getCreativeDtoByOsAndCountry(os,country))
                .andReturn(oneElementsList)
                .andReturn(twoElementsList)
                .andReturn(threeElementsList);
        replay(creativesDaoMock);

        CreativeServiceImpl creativeService = new CreativeServiceImpl(creativesDaoMock);
        List<CreativesDto> oneCreativesDtoList = creativeService.getCreativesOptimized(limit, os, country);
        List<CreativesDto> twoCreativesDtoList = creativeService.getCreativesOptimized(limit, os, country);
        List<CreativesDto> limitedCreativesDtoList = creativeService.getCreativesOptimized(limit, os, country);

        assertEquals(oneElementsList.size(), oneCreativesDtoList.size());
        assertEquals(twoElementsList.size(), twoCreativesDtoList.size());
        assertEquals(limit, limitedCreativesDtoList.size());
    }
}
