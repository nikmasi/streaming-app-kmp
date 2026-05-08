package com.streaming.spring_boot.search.controller

import com.streaming.spring_boot.search.service.SearchService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search")
public class SearchController(
    private val searchService: SearchService
){

}