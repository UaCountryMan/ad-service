package com.zemlyak.web.ad.creative.dao;

import java.util.List;

import com.zemlyak.web.ad.creative.CreativesDto;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import com.zemlyak.web.ad.creative.Creative;
import com.zemlyak.web.ad.creative.CreativesDao;

public class CreativesDaoTemplate implements CreativesDao {
    private static final RowMapper<Creative> CREATIVE_ROW_MAPPER_DEFAULT = new CreativeRowMapper(true);
    private static final RowMapper<CreativesDto> CREATIVE_DTO_ROW_MAPPER_DEFAULT = new CreativeDtoRowMapper();
    private static final String GET_BY_OS_AND_COUNTRY_DEFAULT = "select * from creatives where " +
            "(array_contains(os,?) or (os = () and not array_contains(excluded_os,?))) and "+
            "(array_contains(countries,?) or (countries = () and not array_contains(excluded_countries,?)))";
    
    private JdbcOperations jdbc;
    private String queryGetByOsAndCountry;
    private RowMapper<Creative> creativeRowMapper;
    private RowMapper<CreativesDto> creativeDtoRowMapper;

    public CreativesDaoTemplate(JdbcOperations jdbc) {
        this.jdbc = jdbc;
        this.creativeRowMapper = CREATIVE_ROW_MAPPER_DEFAULT;
        this.queryGetByOsAndCountry = GET_BY_OS_AND_COUNTRY_DEFAULT;
        this.creativeDtoRowMapper = CREATIVE_DTO_ROW_MAPPER_DEFAULT;
    }

    @Override
    public List<Creative> getCreativeByOsAndCountry(String os, String country){
        return jdbc.query(queryGetByOsAndCountry, creativeRowMapper, os, os, country, country);
    }

    @Override
    public List<CreativesDto> getCreativeDtoByOsAndCountry(String os, String country) {
        return jdbc.query(queryGetByOsAndCountry, creativeDtoRowMapper, os, os, country, country);
    }

    public void setCreativeDtoRowMapper(RowMapper<CreativesDto> creativeDtoRowMapper) {
        this.creativeDtoRowMapper = creativeDtoRowMapper;
    }

    public void setCreativeRowMapper(RowMapper<Creative> creativeRowMapper) {
        this.creativeRowMapper = creativeRowMapper;
    }

    public void setQueryGetByOsAndCountry(String queryGetByOsAndCountry) {
        this.queryGetByOsAndCountry = queryGetByOsAndCountry;
    }
}
