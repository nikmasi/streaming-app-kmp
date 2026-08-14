import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { MovieResponse, Playback, WatchProgress } from '../../service/playback';
import { environment } from '../home/home';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-watch-history',
  imports: [DatePipe, CommonModule, TranslatePipe],
  templateUrl: './watch-history.html',
  styleUrl: './watch-history.css',
})
export class WatchHistory implements OnInit {
  constructor(
    private translate: TranslateService
  ) {}

  private playbackService = inject(Playback);

  history: WatchProgress[] = [];

  ngOnInit(): void {
    this.playbackService.getHistory().subscribe({
        next: data => {
          this.history = data;
        },
        error: err => {
          console.error('Failed to load history', err);
        }
      });
  }

  getThumbnailUrlForWatch(movie: MovieResponse): string {
      return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
    }
}
