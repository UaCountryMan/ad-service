package com.zemlyak.web.ad.creative.dao;

import com.zemlyak.web.ad.creative.Creative;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(EasyMockRunner.class)
public class CreativeRowMapperTest {
    @Mock
    private ResultSet resultSet;
    @Mock
    private ResultSet arrayResultSet;
    @Mock
    private Array array;

    @Test
    public void mapRowNoArrayTest() throws SQLException {
        Integer id = 1;
        String url = "someUrl";
        String description = "someDescription";

        expect(resultSet.getInt("id")).andReturn(id);
        expect(resultSet.getString("description")).andReturn(description);
        expect(resultSet.getString("url")).andReturn(url);

        replay(resultSet);

        CreativeRowMapper mapper = new CreativeRowMapper(true);
        Creative creative = mapper.mapRow(resultSet,0);

        assertEquals(id, creative.getId());
        assertEquals(url, creative.getUrl());
        assertEquals(description, creative.getDescription());
        assertNull("os must be null on mode without arrays", creative.getOs());
        assertNull("countries must be null on mode without arrays", creative.getCountries());
        assertNull("excluded os must be null on mode without arrays", creative.getExcludedOs());
        assertNull("excluded countries must be null on mode without arrays", creative.getExcludedCountries());
    }

    @Test
    public void mapRowTest() throws SQLException {
        Integer id = 1;
        String url = "someUrl";
        String description = "someDescription";

        expect(resultSet.getInt("id")).andReturn(id);
        expect(resultSet.getString("description")).andReturn(description);
        expect(resultSet.getString("url")).andReturn(url);

        expect(resultSet.getArray("os")).andReturn(array);
        expect(array.getResultSet()).andReturn(arrayResultSet);
        expect(arrayResultSet.next()).andReturn(false);
        arrayResultSet.close();
        expectLastCall();

        expect(resultSet.getArray("countries")).andReturn(array);
        expect(array.getResultSet()).andReturn(arrayResultSet);
        expect(arrayResultSet.next()).andReturn(false);
        arrayResultSet.close();
        expectLastCall();

        expect(resultSet.getArray("excluded_os")).andReturn(array);
        expect(array.getResultSet()).andReturn(arrayResultSet);
        expect(arrayResultSet.next()).andReturn(false);
        arrayResultSet.close();
        expectLastCall();

        expect(resultSet.getArray("excluded_countries")).andReturn(array);
        expect(array.getResultSet()).andReturn(arrayResultSet);
        expect(arrayResultSet.next()).andReturn(false);
        arrayResultSet.close();
        expectLastCall();

        replay(resultSet,array,arrayResultSet);

        CreativeRowMapper mapper = new CreativeRowMapper(false);
        Creative creative = mapper.mapRow(resultSet,0);

        assertEquals(id, creative.getId());
        assertEquals(url, creative.getUrl());
        assertEquals(description, creative.getDescription());
        assertNotNull("os must be not null on mode with arrays", creative.getOs());
        assertNotNull("countries must be not null on mode with arrays", creative.getCountries());
        assertNotNull("excluded os must be not null on mode with arrays", creative.getExcludedOs());
        assertNotNull("excluded countries must be not null on mode with arrays", creative.getExcludedCountries());
    }
}
