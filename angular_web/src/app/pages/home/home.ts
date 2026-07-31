import { Component, HostListener, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Obavezno uvezi ovo
import { Catalog } from '../../service/catalog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class HomeComponent implements OnInit {
  isScrolled = false;

  catalogService = inject(Catalog);

  router = inject(Router);

  data: any ={}

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 50) {
      this.isScrolled = true;
    } else {
      this.isScrolled = false;
    }
  }

  ngOnInit(): void {
  this.catalogService.home()
    .subscribe({
      next: res => {
        console.log(res);
        this.data = res;
      },
      error: err => {
        console.log(err);
      }
    });
}


  getGenres(): string[] {
    return Object.keys(this.data);
  }

  openMovie(movie: any) {
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


}