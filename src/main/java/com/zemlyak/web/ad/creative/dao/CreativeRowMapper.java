package com.zemlyak.web.ad.creative.dao;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.zemlyak.web.ad.creative.Creative;

public class CreativeRowMapper implements RowMapper<Creative>{
    private boolean withoutArrays = false;

    public CreativeRowMapper(){}

    public CreativeRowMapper(boolean withoutArrays) {
        this.withoutArrays = withoutArrays;
    }

    @Override
    public Creative mapRow(ResultSet rs, int row) throws SQLException {
        return new Creative(
                rs.getInt("id"),
                rs.getString("description"),
                rs.getString("url"),
                withoutArrays ? null : arrayToStringList(rs.getArray("os")),
                withoutArrays ? null : arrayToStringList(rs.getArray("countries")),
                withoutArrays ? null : arrayToStringList(rs.getArray("excluded_os")),
                withoutArrays ? null : arrayToStringList(rs.getArray("excluded_countries"))
        );
    }
    
    private static List<String> arrayToStringList(Array array) throws SQLException{
        List<String> resultList = new ArrayList<>();
        ResultSet rs = array.getResultSet();
        while(rs.next()){
            resultList.add(rs.getString(2));
        }
        rs.close();
        return resultList;
    }
}
