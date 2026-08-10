import { Component, HostListener, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Catalog } from '../../service/catalog';
import { environment } from '../home/home';
import { Movie } from '../../model/movie';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search implements OnInit {
  isScrolled = false;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 50) {
      this.isScrolled = true;
    } else {
      this.isScrolled = false;
    }
  }

  query = '';

  catalogService = inject(Catalog);
  router = inject(Router);

  data: any[] = [];

  searchData: any[] = [];

  ngOnInit(): void {
    this.catalogService.yearTop5().subscribe({
      next: res => {
        this.data = res as any[];
      },
      error: err => console.log(err)
    });
  }

  filteredMovies() {
    return this.data.filter(movie =>
      movie.title.toLowerCase().includes(this.query.toLowerCase())
    );
  }

  

  openMovie(movie:any){
  this.router.navigate(['/movie-details'], {
    state: { movie }
  });
}


  searchMovies(value: string) {
    this.query = value;

    if(this.query.trim().length === 0){
      this.catalogService.yearTop5().subscribe({
        next: res => {
          this.searchData = res as any[];
        }
      });
      return;
    }


    this.catalogService.search(this.query)
      .subscribe({
        next: res => {
          this.searchData = res as any[];
        },
        error: err => console.log(err)
      });
  }

  getThumbnailUrl(movie: Movie): string {
    return `${environment.apiUrl}/api/v1/catalog/${movie.thumbnailUrl}`;
  }

}
