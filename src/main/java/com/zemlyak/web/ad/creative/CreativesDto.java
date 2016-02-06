package com.zemlyak.web.ad.creative;

public class CreativesDto {
    public Integer id;
    public String description;
    public String url;
    
    public CreativesDto(){}
    
    public CreativesDto(Integer id, String description, String url) {
        this.id = id;
        this.description = description;
        this.url = url;
    }
}