package com.zemlyak.web.ad.creative.dao;

import com.zemlyak.web.ad.creative.CreativesDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreativeDtoRowMapper implements RowMapper<CreativesDto> {
    @Override
    public CreativesDto mapRow(ResultSet rs, int row) throws SQLException {
        return new CreativesDto(
                rs.getInt("id"),
                rs.getString("description"),
                rs.getString("url")
        );
    }
}
