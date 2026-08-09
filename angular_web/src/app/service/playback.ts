import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MovieResponse {
  id: string;
  title: string;
  description: string;
  genres: string[];
  duration: number;
  releaseYear: number;
  thumbnailUrl: string;
  videoUrl: string;
}

export interface WatchProgress {
  movie: MovieResponse;
  movieId: string;
  positionSeconds: number;
  durationSeconds: number;
  completed: boolean;
  lastWatchedAt: string;
}

export interface UpdateWatchProgressRequest {
  movieId: string;
  positionSeconds: number;
  durationSeconds: number;
}

@Injectable({
  providedIn: 'root',
})
export class Playback {

  private http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api/v1/playback';

  // privremeno
  private readonly userEmail = 'gica@test.com';

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'X-User-Email': this.userEmail
    });
  }

  getProgress(movieId: string): Observable<WatchProgress | null> {
    return this.http.get<WatchProgress | null>(
      `${this.apiUrl}/progress/${movieId}`,
      {
        headers: this.getHeaders()
      }
    );
  }

  updateProgress(
    movieId: string,
    positionSeconds: number,
    durationSeconds: number
  ): Observable<WatchProgress> {

    const request: UpdateWatchProgressRequest = {
      movieId,
      positionSeconds,
      durationSeconds
    };

    return this.http.put<WatchProgress>(
      `${this.apiUrl}/progress`,
      request,
      {
        headers: this.getHeaders()
      }
    );
  }

  getContinueWatching(): Observable<WatchProgress[]> {
    return this.http.get<WatchProgress[]>(
      `${this.apiUrl}/continue-watching`,
      {
        headers: this.getHeaders()
      }
    );
  }

  getHistory(): Observable<WatchProgress[]> {
    return this.http.get<WatchProgress[]>(
      `${this.apiUrl}/history`,
      {
        headers: this.getHeaders()
      }
    );
  }
}