package com.zemlyak.web.ad.creative.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.zemlyak.web.ad.creative.CreativesDto;
import com.zemlyak.web.ad.creative.CreativeService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AdServiceRest {
    private CreativeService creativeService;
    
    @Autowired
    public AdServiceRest(CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @RequestMapping(value="/rest/ad/list", method=RequestMethod.POST, produces = "application/json")
    public CreativesResponse getCreativesByPost(@RequestBody CreativesRequest request){
        validateRequest(request);

        List<CreativesDto> dtoList = creativeService.getCreatives(request.limit, request.os, request.country);
        return new CreativesResponse(dtoList);
    }

    @RequestMapping(value="/rest/optimized/ad/list", method=RequestMethod.POST, produces = "application/json")
    public CreativesResponse getCreativesOptimizedByPost(@RequestBody CreativesRequest request){
        validateRequest(request);

        List<CreativesDto> dtoList = creativeService.getCreativesOptimized(request.limit, request.os, request.country);
        return new CreativesResponse(dtoList);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    private static void validateRequest(CreativesRequest request){
        if(request.os == null || request.os.isEmpty()){
            throw new IllegalArgumentException("Missing required parameter 'os'");
        }

        if(request.country == null || request.country.isEmpty()){
            throw new IllegalArgumentException("Missing required parameter 'country'");
        } else if(request.country.length() != 2){
            throw new IllegalArgumentException("Incorrect 'country' parameter format. Use ISO 2 symbol format");
        }

        if(request.limit == null){
            throw new IllegalArgumentException("Missing required parameter 'limit'");
        } else if(request.limit < 0){
            throw new IllegalArgumentException("Incorrect 'limit' parameter format. Use unsigned integer number.");
        }
    }
}
