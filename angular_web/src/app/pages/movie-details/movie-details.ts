import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-movie-details',
  imports: [CommonModule],
  templateUrl: './movie-details.html',
  styleUrl: './movie-details.css',
})
export class MovieDetails {

  movie = history.state.movie;
}
