package com.example.Enotes.controller;

import com.example.Enotes.endpoints.CacheEndpoint;
import com.example.Enotes.service.CacheManagerService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CacheController implements CacheEndpoint {

    @Autowired
    private CacheManagerService cacheManagerService;

    @Override
    public ResponseEntity<?> getAllCache() {
        Collection<String> cache = cacheManagerService.getCache();
        return CommonUtil.createBuildResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCache(String cache_name) {
        Cache cache = cacheManagerService.getCacheName(cache_name);
        return CommonUtil.createBuildResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeAllCache() {
        cacheManagerService.removeAllCache();
        return CommonUtil.createBuildResponseMessage("Removed All cache", HttpStatus.OK);
    }
}
