import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Catalog } from '../../service/catalog';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search implements OnInit {

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

}
