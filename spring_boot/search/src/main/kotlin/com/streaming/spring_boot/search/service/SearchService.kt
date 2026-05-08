package com.streaming.spring_boot.search.service

import com.streaming.spring_boot.search.repository.SearchRepository
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val searchRepository: SearchRepository
) {

}