package com.streaming.spring_boot.catalog.controller

import com.streaming.spring_boot.catalog.client.PlaybackClient
import com.streaming.spring_boot.catalog.model.Movie
import com.streaming.spring_boot.catalog.service.MovieService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/catalog")
@CrossOrigin(origins = ["http://localhost:4200"])
class MovieController(
    private val movieService: MovieService,
    private val playbackClient: PlaybackClient
    //private val userService: UserService
) {
    data class MoviesTitleRequest(
        val title: String,
        val email: String
    )

    data class CatalogInfoResponse(
        val movieNumber: Int,
        val tvShowNumber: Int
    )

    data class MovieResponse(
        val id: String,
        val title: String,
        val description: String,
        val genres: List<String>,
        val duration: Int,
        val releaseYear: Int,
        val thumbnailUrl: String,
        val videoUrl: String
    )


    private fun Movie.toResponse() =
        MovieResponse(
            id = id?.toHexString() ?: "",
            title = title,
            description = description,
            genres = genres,
            duration = duration,
            releaseYear = releaseYear,
            thumbnailUrl = thumbnailUrl,
            videoUrl = videoUrl
        )


//    data class MyListRequest(
//        val email: String,
//        val type: ListType
//    )
//
//    data class AddMyListRequest(
//        val movieId: ObjectId,
//        val email: String,
//        val type: ListType
//    )

    @GetMapping("/search")
    fun search(
        @RequestParam title: String,
        @RequestParam email: String
    ): List<Movie>? {
        //userService.saveSearchContent(title,email)
        return movieService.search(title)
    }

    @GetMapping("/yearTop5")
    fun yearTop5(): List<Movie>? {
        println("ovde")
        val movies =movieService.yearTop5()
        println(movies.toString())
        return movies
    }

    @GetMapping("/top5ByGenreOrderByYear")
    fun top5ByGenreOrderByYear(@RequestParam genre: String): List<Movie>? {
        return movieService.top5ByGenreOrderByYear(genre)
    }

    @GetMapping("/home")
    fun getHomeData(): Map<String, List<MovieResponse>> {
        val genres =
            listOf("Drama", "Romance", "Action", "Sci-Fi","Thriller", "Crime", "Adventure", "Music",
                "Comedy", "History", "Mystery", "Sport", "War", "Fantasy", "Animation")

        //val allGenres = movieService.allGenres()

        return genres.associateWith { genre ->
            movieService.getHomeData(genre)
        }
    }

    @GetMapping("/movie/{id}")
    fun getMovieById(@PathVariable id: String): MovieResponse? {
        return movieService.getMovieById(id)?.toResponse()
    }

    @GetMapping("/movies/by-ids")
    fun getMoviesByIds(@RequestParam ids: List<String>): List<MovieResponse> {
        return movieService.getMoviesByIds(ids).map { it.toResponse() }
    }

//    @PostMapping("/my-list")
//    fun myList(
//        //@Valid
//        @RequestBody body: MyListRequest
//    ): List<Movie>{
//        return movieService.getMyListMovies(body.email, body.type)
//    }
//
//    @PostMapping("/add-my-list")
//    fun addMyList(
//        //@Valid
//        @RequestBody body: AddMyListRequest
//    ): Boolean{
//        return movieService.addToMyListMovies(body.email, body.movieId, body.type)
//    }
//
//    @PostMapping("/remove-my-list")
//    fun removeFromMyList(@RequestBody request: AddMyListRequest): Boolean {
//        return movieService.removeFromMyList(request.email, request.movieId, request.type)
//    }

}