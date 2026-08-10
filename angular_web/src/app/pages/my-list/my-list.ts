import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Favourite } from '../../service/favourite';
import { Catalog } from '../../service/catalog';
import { Movie } from '../../model/movie';
import { MovieResponse } from '../../service/playback';
import { UserContentPreference } from '../../model/favourite';
import { environment } from '../home/home';

@Component({
  selector: 'app-my-list',
  imports: [CommonModule],
  templateUrl: './my-list.html',
  styleUrl: './my-list.css',
})
export class MyList implements OnInit {

  private favouriteService = inject(Favourite);
  private catalogService = inject(Catalog);

  likedMovies: MovieResponse[] = [];

  watchLaterMovies: MovieResponse[] = [];

  isLoading = true;

  email = ""

  ngOnInit(): void {
    this.loadPreferences();
    this.email = JSON.stringify(localStorage.getItem("email"))
  }

  private loadPreferences(): void {
    this.isLoading = true;

    this.email = JSON.stringify(localStorage.getItem("email"))
    this.favouriteService.getByStatus(this.email, 'LIKED')
      .subscribe({
        next: preferences => {
          this.loadLikedContent(preferences);
        },
        error: error => {
          console.error('Failed to load liked content', error);
          this.isLoading = false;
        }
      });

    this.favouriteService
      .getByStatus(this.email, 'WATCH_LATER')
      .subscribe({
        next: preferences => {
          this.loadWatchLaterContent(preferences);
        },
        error: error => {
          console.error('Failed to load watch later content', error);
        }
      });
  }

  private loadLikedContent(
    preferences: UserContentPreference[]
  ): void {

    const moviePreferences = preferences.filter(
      p => p.contentType === 'MOVIE'
    );


    moviePreferences.forEach(preference => {
      this.catalogService
        .getMovie(preference.contentId)
        .subscribe({
          next: movie => {
            this.likedMovies.push(movie);
          },
          error: error => {
            console.error(
              `Failed to load movie ${preference.contentId}`,
              error
            );
          }
        });
    });

    this.isLoading = false;
  }

  private loadWatchLaterContent(
    preferences: UserContentPreference[]
  ): void {

    const moviePreferences = preferences.filter(
      p => p.contentType === 'MOVIE'
    );


    moviePreferences.forEach(preference => {
      this.catalogService.getMovie(preference.contentId).subscribe({
          next: movie => {
            this.watchLaterMovies.push(movie);
          },
          error: error => {
            console.error(
              `Failed to load movie ${preference.contentId}`,
              error
            );
          }
        });
    });

  }

  removeFromLiked(movie: Movie): void {
  }

  removeFromWatchLater(movie: Movie): void {
  }

  getThumbnailUrlForWatch(movie: MovieResponse): string {
    return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
  }
}
