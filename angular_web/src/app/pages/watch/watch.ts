import { Component, ElementRef, ViewChild, AfterViewInit, OnDestroy, inject } from '@angular/core';
import Hls from 'hls.js';
import { Playback } from '../../service/playback';

@Component({
  selector: 'app-watch',
  templateUrl: './watch.html',
  styleUrl: './watch.css'
})
export class Watch implements AfterViewInit, OnDestroy {

  @ViewChild('player')
  video!: ElementRef<HTMLVideoElement>;

  private playback = inject(Playback);

  movie = history.state.movie;

  private hls?: Hls;

  private saveInterval?: ReturnType<typeof setInterval>;

  // pozicija koju smo dobili iz backenda
  private savedPosition = 0;

  ngAfterViewInit() {

    if (!this.movie) {
      console.error('Movie nije pronađen u history.state');
      return;
    }

    // Prvo učitamo progress
    this.loadProgress();

    // Onda pokrenemo video
    this.initPlayer();
  }

  private loadProgress() {
    this.playback.getProgress(this.movie.id).subscribe({
        next: (progress) => {

          if (progress) {

            this.savedPosition = progress.positionSeconds;

            console.log('Prethodna pozicija:', this.savedPosition);

            // ako je video vec spreman, odmah postavi poziciju
            this.setSavedPosition();
          }
        },
        error: (error) => {
          // Nema progress-a = prvi put gleda film
          console.log('Nema prethodnog progress-a.', error);
          this.savedPosition = 0;
        }
      });
  }

  private initPlayer() {
    const url ='http://localhost:8080' + this.movie.videoUrl;

    const video = this.video.nativeElement;

    // kada browser dobije metadata, mozemo postaviti currentTime.
    video.addEventListener('loadedmetadata', () => { this.setSavedPosition();});

    // ako korisnik pauzira, odmah sacuvaj poziciju
    video.addEventListener('pause', () => { this.saveProgress();});

    // ako zavrsi film, sacuvaj poslednju poziciju
    video.addEventListener('ended', () => { this.saveProgress();});

    if (Hls.isSupported()) {
      this.hls = new Hls();
      this.hls.loadSource(url);
      this.hls.attachMedia(video);
    } else if (
      video.canPlayType(
        'application/vnd.apple.mpegurl'
      )
    ) {
      video.src = url;
    }

    // cuvaj progress svakih 5 sekundi
    this.saveInterval = setInterval(() => {
      this.saveProgress();
    }, 5000);
  }

  private setSavedPosition() {
    const video = this.video.nativeElement;

    if (
      this.savedPosition > 0 &&
      video.duration &&
      !isNaN(video.duration)
    ) {

      // zastita da currentTime ne bude veci od trajanja videa
      if (this.savedPosition < video.duration) {
        video.currentTime = this.savedPosition;
        console.log(`Nastavljamo od ${this.savedPosition} sekundi`);
      }
    }
  }

  private saveProgress() {
    const video = this.video?.nativeElement;

    if (!video) {
      return;
    }
    if (!this.movie?.id) {
      return;
    }
    if (!video.duration || isNaN(video.duration)) {
      return;
    }
    if (video.currentTime <= 0) {
      return;
    }
    const positionSeconds = Math.floor(video.currentTime);
    const durationSeconds = Math.floor(video.duration);

    console.log('Čuvam progress:', positionSeconds,'/',durationSeconds);

    this.playback.updateProgress(this.movie.id, positionSeconds, durationSeconds).subscribe({
        next: (progress) => {
          console.log('Progress sačuvan:',progress);
        },

        error: (error) => {
          console.error('Greška pri čuvanju progress-a:', error);
        }
      });
  }

  ngOnDestroy() {
    // sacuvaj poziciju kada korisnik napusti watch komponentu
    this.saveProgress();
    if (this.saveInterval) {
      clearInterval(this.saveInterval);
    }
    this.hls?.destroy();
  }
}