package com.streaming.spring_boot.catalog.controller

import com.streaming.spring_boot.catalog.client.PlaybackClient
import com.streaming.spring_boot.catalog.model.Movie
import com.streaming.spring_boot.catalog.service.MovieService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/catalog")
@CrossOrigin(origins = ["http://localhost:4200"])
class AdminController(
    private val movieService: MovieService,
    private val playbackClient: PlaybackClient
    //private val userService: UserService
) {
    data class MovieResponse(
        val id: String,
        val title: String,
        val description: String,
        val genres: List<String>,
        val duration: Int,
        val releaseYear: Int,
        val thumbnailUrl: String,
        val videoUrl: String,
    )

    data class DeleteMovieRequest(
        val id: String
    )

    data class CategoryResponse(
        val name: String,
        val movieCount: Long
    )

    data class AnalyticsResponse(
        val totalMovies: Long,
        val totalGenres: Long,
        val averageDuration: Double,
        val moviesByGenre: List<GenreAnalytics>
    )

    data class GenreAnalytics(
        val name: String,
        val movieCount: Long
    )

    @PostMapping("/admin/movie")
    fun uploadMovie(
        @RequestParam title: String,
        @RequestParam description: String,
        @RequestParam genres: String,
        @RequestParam duration: Int,
        @RequestParam releaseYear: Int,
        @RequestParam thumbnail: MultipartFile,
        @RequestParam video: MultipartFile
    ): Movie {
        // save thumbnail
        val thumbnailPath = movieService.saveFile(thumbnail, "storage/thumbnails")

        // save database
        var movie = Movie(
            title = title,
            description = description,
            genres = genres.split(","),
            duration = duration,
            releaseYear = releaseYear,
            thumbnailUrl = thumbnailPath.toString(),
            videoUrl = ""
        )

        movie  = movieService.saveMovie(movie);

        val playback =
            playbackClient.uploadVideo(
                movie.id!!.toHexString(),
                video
            )

        movie = movie.copy(videoUrl = playback.streamUrl)

        return movieService.saveMovie(movie)
    }

    @GetMapping("/admin/info")
    fun getInfo(): MovieController.CatalogInfoResponse {
        return movieService.getInfo()
    }

    // movies

    @GetMapping("/admin/movies")
    fun getAllMovies(): ResponseEntity<List<MovieResponse>> {
        return ResponseEntity.ok(movieService.getAllMovies())
    }

    @PostMapping("/admin/edit-movie")
    fun editMovie(
        @RequestBody request: MovieResponse
    ): ResponseEntity<MovieResponse> {
        return ResponseEntity.ok(movieService.editMovie(request))
    }

    @DeleteMapping("/admin/delete-movie")
    fun deleteMovie(
        @RequestBody request: DeleteMovieRequest
    ): ResponseEntity<String> {
        movieService.deleteMovie(request.id)
        return ResponseEntity.ok("Movie deleted")
    }

    @GetMapping("/admin/categories")
    fun getCategories(): ResponseEntity<List<CategoryResponse>> {
        return ResponseEntity.ok(movieService.getCategories())
    }

    @GetMapping("/admin/analytics")
    fun getAnalytics(): ResponseEntity<AnalyticsResponse> {
        return ResponseEntity.ok(movieService.getAnalytics())
    }
}