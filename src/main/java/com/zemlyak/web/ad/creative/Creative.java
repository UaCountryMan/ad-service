package com.zemlyak.web.ad.creative;

import java.util.List;

public class Creative {
    private Integer id;
    private String description;
    private String url;
    private List<String> os;
    private List<String> countries;
    private List<String> excludedOs;
    private List<String> excludedCountries;
    
    public Creative(Integer id, String description, String url, List<String> os, List<String> countries,
            List<String> excludedOs, List<String> excludedCountries) {
        this.id = id;
        this.description = description;
        this.url = url;
        this.os = os;
        this.countries = countries;
        this.excludedOs = excludedOs;
        this.excludedCountries = excludedCountries;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getOs() {
        return os;
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<String> getExcludedOs() {
        return excludedOs;
    }

    public List<String> getExcludedCountries() {
        return excludedCountries;
    }
}
