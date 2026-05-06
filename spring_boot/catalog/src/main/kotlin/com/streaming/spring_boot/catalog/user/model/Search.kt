package com.streaming.spring_boot.catalog.user.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "search",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["userid", "query"])
    ]
)
data class Search(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idSearch: Long = 0,

    @Column(name = "userid", nullable = false)
    val idUser: Long=0,

    @Column(nullable = false)
    val query: String="",
    var count: Int = 1,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)