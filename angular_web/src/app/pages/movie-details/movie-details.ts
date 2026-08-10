import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { environment } from '../home/home';
import { MovieResponse, WatchProgress } from '../../service/playback';
import { Favourite } from '../../service/favourite';
import { ContentType } from '../../model/favourite';

@Component({
  selector: 'app-movie-details',
  imports: [CommonModule],
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.css',
})
export class MovieDetails implements OnInit{

  router = inject(Router);

  movie: MovieResponse;
  watchProgress?: WatchProgress;

  private favouriteService = inject(Favourite);

  email = ""

  constructor() {
    this.watchProgress = history.state.watch;

    this.movie = history.state.movie ?? this.watchProgress?.movie;
  }

  ngOnInit(): void {
    this.email = JSON.stringify(localStorage.getItem("email"))
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


  isLiked = false;
  isDisliked = false;
  isWatchLater = false;

  toggleLike(): void {

    if (this.isLiked) {
      this.removePreference();
      return;
    }


    this.favouriteService.setPreference(
      this.email,
      {
        
        contentId: this.movie.id,
        contentType: 'MOVIE',
        status: 'LIKED'
      }
    ).subscribe(() => {

      this.isLiked = true;
      this.isDisliked = false;

    });
  }

  toggleDislike(): void {

    if (this.isDisliked) {
      this.removePreference();
      return;
    }

    this.favouriteService.setPreference(
      this.email,
      {
        contentId: this.movie.id,
        contentType: 'MOVIE',
        status: 'DISLIKED'
      }
    ).subscribe(() => {
      this.isDisliked = true;
      this.isLiked = false;
    });
  }

  toggleWatchLater(): void {

    if (this.isWatchLater) {
      this.removePreference();
      return;
    }

    this.favouriteService.setPreference(
      this.email,
      {
        contentId: this.movie.id,
        contentType: 'MOVIE',
        status: 'WATCH_LATER'
      }
    ).subscribe(() => {this.isWatchLater = true;});
  }

  private removePreference(): void {

    this.favouriteService.removePreference(this.email,'MOVIE',this.movie.id).subscribe(() => {
      this.isLiked = false;
      this.isDisliked = false;
      this.isWatchLater = false;
    });
  }
}