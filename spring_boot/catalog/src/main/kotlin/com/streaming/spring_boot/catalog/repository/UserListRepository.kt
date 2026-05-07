package com.streaming.spring_boot.catalog.repository
//
//import org.springframework.data.jpa.repository.JpaRepository
//
//interface UserListRepository: JpaRepository<UserList, Long> {
//
//    fun findByUserIdAndType(userId: Long, type: ListType): List<UserList>
//
//    fun findByUserIdAndMovieIdAndType(
//        userId: Long,
//        movieId: Long,
//        type: ListType
//    ): UserList?
//
////    @Query("""
////        SELECT m FROM Movie m
////        JOIN UserList uml ON uml.movieId = m.id
////        WHERE uml.userId = :userId AND uml.type = :type
////    """)
////    fun findMyListMovies(
////        userId: Long,
////        type: ListType
////    ): List<Movie>
//
//    fun deleteByUserIdAndMovieIdAndType(
//        userId: Long,
//        movieId: Long,
//        type: ListType
//    )
//}