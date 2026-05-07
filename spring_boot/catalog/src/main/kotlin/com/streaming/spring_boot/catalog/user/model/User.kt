package com.streaming.spring_boot.catalog.user.model
//
//import jakarta.persistence.*
//import java.time.Instant
//
//@Entity
//@Table(name = "users") //reserved User
//data class User(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0,
//
//    @Column(nullable = false, unique = true)
//    val email: String,
//
//    @Column(name = "\"hashedPassword\"", nullable = false)
//    var hashedPassword: String,
//    val createdAt: Instant = Instant.now(),
//
//    @Column(nullable = false, name = "\"fullname\"")
//    val fullName: String,
//
//    @Column(nullable = false, name = "\"phone\"")
//    val phone: String,
//
//    @Enumerated(EnumType.STRING)
//    val role: Role,
//
//    @Column(nullable = false, name = "\"profileImage\"")
//    var profileImage: String? = null
//
//)