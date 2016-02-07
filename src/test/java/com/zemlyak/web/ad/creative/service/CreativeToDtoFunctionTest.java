package com.zemlyak.web.ad.creative.service;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativesDto;
import org.easymock.EasyMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class CreativeToDtoFunctionTest {
    @Test
    public void applyTest(){
        Integer id = 1;
        String url = "someUrl";
        String description = "someDescription";

        CreativeToDtoFunction function = new CreativeToDtoFunction();
        CreativesDto creativesDto = function.apply(new Creative(id, description, url, null, null, null, null));

        assertEquals(id, creativesDto.id);
        assertEquals(url, creativesDto.url);
        assertEquals(description, creativesDto.description);
    }
}
