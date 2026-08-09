import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { environment } from '../home/home';
import { MovieResponse, WatchProgress } from '../../service/playback';

@Component({
  selector: 'app-movie-details',
  imports: [CommonModule],
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.css',
})
export class MovieDetails {

  router = inject(Router);

  movie: MovieResponse;
  watchProgress?: WatchProgress;

  constructor() {
    this.watchProgress = history.state.watch;

    this.movie = history.state.movie ?? this.watchProgress?.movie;
  }

  getThumbnailUrl(movie: MovieResponse): string {
    return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
  }

  playMovie(movie: MovieResponse) {
    this.router.navigate(
      ['/watch'],
      {
        state: {
          movie,
          watchProgress: this.watchProgress
        }
      }
    );
  }
}