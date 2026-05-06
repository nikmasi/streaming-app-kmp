package com.streaming.spring_boot.catalog.user.repository

import com.streaming.spring_boot.catalog.user.model.Search
import org.springframework.data.jpa.repository.JpaRepository

interface SearchRepository: JpaRepository<Search, Long> {

    fun findByIdUserAndQuery(userId:Long, query:String): Search?

    fun findTop10ByIdUserOrderByCreatedAtDesc(userId: Long): List<Search>
}