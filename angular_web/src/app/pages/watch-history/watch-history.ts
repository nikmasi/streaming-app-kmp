import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Playback, WatchProgress } from '../../service/playback';

@Component({
  selector: 'app-watch-history',
  imports: [DatePipe, CommonModule],
  templateUrl: './watch-history.html',
  styleUrl: './watch-history.css',
})
export class WatchHistory implements OnInit {
  
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
}
