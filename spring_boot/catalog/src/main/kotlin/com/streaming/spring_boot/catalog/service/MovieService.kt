package com.streaming.spring_boot.catalog.service

//import com.streaming.spring_boot.catalog.model.ListType
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import com.streaming.spring_boot.catalog.repository.MovieRepository
//import com.streaming.spring_boot.catalog.repository.UserListRepository
//import com.streaming.spring_boot.catalog.user.repository.UserRepository
import com.streaming.spring_boot.catalog.model.Movie
//import com.streaming.spring_boot.catalog.model.UserList
import org.bson.types.ObjectId

@Service
class MovieService(
    private val movieRepository: MovieRepository,
    //private val userRepository: UserRepository,
    //private val userListRepository: UserListRepository
) {

    fun search(title: String): List<Movie>?{
        val movies = movieRepository.findByTitleContainingIgnoreCase(title)
        return movies
    }

    fun yearTop5(): List<Movie>? {
        val movies = movieRepository.findTop5ByOrderByReleaseYearDesc()
        println("ovdee "+ movies.toString())
        val allMovies = movieRepository.findAll()
        println("Ukupno filmova u bazi: ${allMovies.size}")
        return movies
    }

    fun top5ByGenreOrderByYear(genre: String): List<Movie>? {
        val movies = movieRepository.findTop5ByGenresOrderByReleaseYearDesc(genre)
        return movies
    }

    fun getHomeData(genre: String):List<Movie>{
        return movieRepository.findTop10ByGenres(genre)
    }

    fun allGenres():List<List<String>>{
        val allGenres = movieRepository.findAll()
            .map { it.genres }
            .distinct()
        return allGenres
    }

//    fun getMyListMovies(email: String, type: ListType): List<Movie>{
//        val user = userRepository.findByEmail(email.trim())
//            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
//
//        return emptyList()
//        //userListRepository.findMyListMovies(userId = user.id, type = type)
//    }
//
//    fun addToMyListMovies(email: String, movieId: ObjectId, type: ListType): Boolean{
//        val user = userRepository.findByEmail(email.trim())
//            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
//
//        try {
//            //userListRepository.save(UserList(userId = user.id, movieId = movieId, type = type))
//        }
//        catch (e: Exception){
//            return false
//        }
//        return true
//    }
//
//
//    @Transactional
//    fun removeFromMyList(email: String, movieId: ObjectId, type: ListType): Boolean {
//        val user = userRepository.findByEmail(email.trim())
//            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
//
//        val movie = movieRepository.findById(movieId)
//            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.") }
//
////        userListRepository.deleteByUserIdAndMovieIdAndType(
////            userId = user.id, movieId = movie.id, type = type
////        )
//
//        return true
//    }
}