package com.streaming.spring_boot.catalog.model

//import jakarta.persistence.*
//import java.time.LocalDateTime
//
//@Entity
//@Table(
//    name = "user_list",
//    uniqueConstraints = [
//        UniqueConstraint(columnNames = ["user_id", "movie_id", "type"])
//    ]
//)
//data class UserList(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0,
//
//    @Column(name = "user_id", nullable = false)
//    var userId: Long = 0,
//
//    @Column(name = "movie_id", nullable = false)
//    var movieId: Long = 0,
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    var type: ListType = ListType.MY_LIST,
//
//    @Column(name = "created_at")
//    var createdAt: LocalDateTime = LocalDateTime.now()
//)
//
//enum class ListType {
//    MY_LIST,
//    FAVORITE
//}