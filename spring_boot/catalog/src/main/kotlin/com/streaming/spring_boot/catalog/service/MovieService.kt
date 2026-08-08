package com.streaming.spring_boot.catalog.service

//import com.streaming.spring_boot.catalog.model.ListType
import com.streaming.spring_boot.catalog.controller.AdminController
import com.streaming.spring_boot.catalog.controller.MovieController
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
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID
import kotlin.String

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

    fun saveFile(file: MultipartFile, folder: String): Path {
        val dir = Paths.get(folder)
        Files.createDirectories(dir)
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val path = dir.resolve(fileName)
        file.transferTo(path)
        return path
    }

    fun saveMovie(movie: Movie): Movie{
        return movieRepository.save(movie);
    }

    fun getInfo(): MovieController.CatalogInfoResponse {
        val movie=movieRepository.findAll().size

        return MovieController.CatalogInfoResponse(
            movieNumber = movie,
            tvShowNumber = 0
        )
    }

    // admin

    fun getAllMovies(): List<AdminController.MovieResponse> {
        return movieRepository.findAll()
            .map { movie ->
                AdminController.MovieResponse(
                    title = movie.title,
                    description = movie.description,
                    genres = movie.genres,
                    duration = movie.duration,
                    releaseYear = movie.releaseYear,
                    thumbnailUrl = movie.thumbnailUrl,
                    videoUrl = movie.videoUrl,
                    id = movie.id?.toHexString() ?: ""
                )
            }
    }

    fun editMovie(request: AdminController.MovieResponse): AdminController.MovieResponse {

        val objectId = ObjectId(request.id)
        val movie = movieRepository.findById(objectId).orElse(null)

        val updatedMovie = movie.copy(
            title = request.title,
            description = request.description,
            genres = request.genres,
            duration = request.duration,
            releaseYear = request.releaseYear,
            thumbnailUrl = request.thumbnailUrl,
            videoUrl = request.videoUrl
        )

        movieRepository.save(updatedMovie)

        return AdminController.MovieResponse(
            title = request.title,
            description = request.description,
            genres = request.genres,
            duration = request.duration,
            releaseYear = request.releaseYear,
            thumbnailUrl = request.thumbnailUrl,
            videoUrl = request.videoUrl,
            id = updatedMovie.id?.toHexString() ?: ""
        )

    }

    fun deleteMovie(id: String) {
        val objectId = ObjectId(id)
        val movie = movieRepository.findById(objectId).orElse(null)
        movieRepository.delete(movie)
    }

    fun getCategories(): List<AdminController.CategoryResponse> {

        return movieRepository.findAll()
            .flatMap { movie -> movie.genres }
            .groupingBy { it }
            .eachCount()
            .map { (genre, count) ->
                AdminController.CategoryResponse(
                    name = genre,
                    movieCount = count.toLong()
                )
            }
            .sortedBy { it.name }
    }

    fun getAnalytics(): AdminController.AnalyticsResponse {
        val movies = movieRepository.findAll()
        val totalMovies = movies.size.toLong()

        val genres = movies.flatMap { it.genres }.distinct()

        val averageDuration = if (movies.isNotEmpty()) {
            movies.map { it.duration }.average()
        } else {
            0.0
        }

        val moviesByGenre = movies
            .flatMap { movie -> movie.genres.map { genre -> genre } }
            .groupingBy { it }
            .eachCount()
            .map { (genre, count) ->
                AdminController.GenreAnalytics(
                    name = genre,
                    movieCount = count.toLong()
                )
            }
            .sortedByDescending { it.movieCount }

        return AdminController.AnalyticsResponse(
            totalMovies = totalMovies,
            totalGenres = genres.size.toLong(),
            averageDuration = averageDuration,
            moviesByGenre = moviesByGenre
        )
    }
}