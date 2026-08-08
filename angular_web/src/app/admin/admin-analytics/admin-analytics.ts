import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Admin } from '../../service/admin';
import { RouterLink, RouterLinkActive } from '@angular/router';

interface GenreAnalytics {
  name: string;
  movieCount: number;
}

interface Analytics {
  totalMovies: number;
  totalGenres: number;
  averageDuration: number;
  moviesByGenre: GenreAnalytics[];
}

@Component({
  selector: 'app-admin-analytics',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-analytics.html',
  styleUrl: './admin-analytics.css'
})
export class AdminAnalytics implements OnInit {

  analytics: Analytics | null = null;

  adminService = inject(Admin)

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadAnalytics();
  }

  loadAnalytics(): void {
    this.adminService.getAnalytics()
      .subscribe({
        next: data => { this.analytics = data;},
        error: error => {
          console.error('Failed to load analytics', error);
        }
      });
  }
}
