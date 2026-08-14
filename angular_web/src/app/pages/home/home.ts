import { Component, HostListener, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Obavezno uvezi ovo
import { Catalog } from '../../service/catalog';
import { Router } from '@angular/router';
import { Movie } from '../../model/movie';
import { MovieResponse, Playback, WatchProgress } from '../../service/playback';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

export const environment = {
  apiUrl: 'http://localhost:8222'
};

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, TranslatePipe], 
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {
  constructor(
    private translate: TranslateService
  ) {}
  
  isScrolled = false;

  catalogService = inject(Catalog);

  router = inject(Router);

  data: any ={}

  private playbackService = inject(Playback);
  history: WatchProgress[] = [];

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 50) {
      this.isScrolled = true;
    } else {
      this.isScrolled = false;
    }
  }

  ngOnInit(): void {
    this.catalogService.home().subscribe({
      next: res => {
        console.log(res);
        this.data = res;
      },
      error: err => {
        console.log(err);
      }
    });

    this.playbackService.getHistory().subscribe({
      next: data => {
        this.history = data;
      },
      error: err => {
        console.error('Failed to load history', err);
      }
    });
  }


  getGenres(): string[] {
    return Object.keys(this.data);
  }

  openMovie(movie: any) {

    console.log('Movie je ', movie);
    this.router.navigate(
      ['/movie-details', movie.id],
      {
        state: { movie }
      }
    );
  }


  search(){
    this.router.navigate(['/search'])
  }

  profile(){
    this.router.navigate(['/profile'])
  }

  getThumbnailUrl(movie: Movie): string {
    return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
  }

  getThumbnailUrlForWatch(movie: MovieResponse): string {
    return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
  }

  continueWatching(watch: WatchProgress){
    this.router.navigate(
      ['/movie-details', watch.movie.id],
      {
        state: { watch }
      }
    );
  }


}