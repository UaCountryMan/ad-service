package com.zemlyak.web.ad.creative.dao;

import com.zemlyak.web.ad.creative.CreativesDto;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class CreativeDtoRowMapperTest {
    @Mock
    private ResultSet resultSet;

    @Test
    public void mapRowTest() throws SQLException {
        Integer id = 1;
        String url = "someUrl";
        String description = "someDescription";

        expect(resultSet.getInt("id")).andReturn(id);
        expect(resultSet.getString("description")).andReturn(description);
        expect(resultSet.getString("url")).andReturn(url);

        replay(resultSet);

        CreativeDtoRowMapper mapper = new CreativeDtoRowMapper();
        CreativesDto dto = mapper.mapRow(resultSet, 0);

        assertEquals(id, dto.id);
        assertEquals(url, dto.url);
        assertEquals(description, dto.description);
    }
}
