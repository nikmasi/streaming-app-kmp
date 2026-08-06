import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Movie } from '../../model/movie';
import { environment } from '../home/home';
import { Router } from '@angular/router';

@Component({
  selector: 'app-movie-details',
  imports: [CommonModule],
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.css',
})
export class MovieDetails {

  router = inject(Router);

  movie = history.state.movie;

  getThumbnailUrl(movie: Movie): string {
    return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
  }

  playMovie(movie: any) {
    this.router.navigate(
      ['/watch'],
      {
        state: { movie }
      }
    );
  }
}
