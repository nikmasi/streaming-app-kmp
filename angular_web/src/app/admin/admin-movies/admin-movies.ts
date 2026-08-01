import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-admin-movies',
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-movies.html',
  styleUrl: './admin-movies.css',
})
export class AdminMovies {

}
