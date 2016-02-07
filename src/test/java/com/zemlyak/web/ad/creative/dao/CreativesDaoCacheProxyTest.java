package com.zemlyak.web.ad.creative.dao;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativesDao;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class CreativesDaoCacheProxyTest {
    @Mock
    private CreativesDao creativesDaoMock;

    @Test
    public void saveToCacheTest() {
        Integer testId = 1;
        String testOs = "ios";
        String testCountry = "US";
        String testUrl = "testUrl";
        String testDescription = "testDescription";
        List<Creative> creativeListForReturn = Arrays.asList(new Creative(testId,testDescription,testUrl,null,null,null,null));

        expect(creativesDaoMock.getCreativeByOsAndCountry(testOs, testCountry)).andReturn(creativeListForReturn).times(1);
        replay(creativesDaoMock);

        CreativesDaoCacheProxy daoCacheProxy = new CreativesDaoCacheProxy(creativesDaoMock);

        List<Creative> creativeList = daoCacheProxy.getCreativeByOsAndCountry(testOs, testCountry);
        List<Creative> creativeListFromCache = daoCacheProxy.getCreativeFromCache(testOs, testCountry);
        assertEquals(creativeListForReturn, creativeList);
        assertEquals(creativeListForReturn, creativeListFromCache);

        verify(creativesDaoMock);
    }

    @Test
    public void returnFromCacheTest() {
        Integer testId = 1;
        String testOs = "ios";
        String testCountry = "US";
        String testUrl = "testUrl";
        String testDescription = "testDescription";
        List<Creative> creativeListForReturn = Arrays.asList(new Creative(testId,testDescription,testUrl,null,null,null,null));

        replay(creativesDaoMock);

        CreativesDaoCacheProxy daoCacheProxy = new CreativesDaoCacheProxy(creativesDaoMock);
        daoCacheProxy.saveCreativeToCache(testOs, testCountry, creativeListForReturn);

        List<Creative> creativeList = daoCacheProxy.getCreativeByOsAndCountry(testOs, testCountry);
        List<Creative> creativeListFromCache = daoCacheProxy.getCreativeFromCache(testOs, testCountry);
        assertEquals(creativeListForReturn, creativeList);
        assertEquals(creativeListForReturn, creativeListFromCache);

        verify(creativesDaoMock);
    }
}
