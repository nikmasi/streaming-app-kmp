import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Admin } from '../../service/admin';
import { RouterLink, RouterLinkActive } from '@angular/router';

interface Category {
  name: string;
  movieCount: number;
}

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-categories.html',
  styleUrl: './admin-categories.css'
})
export class AdminCategories implements OnInit {

  categories: Category[] = [];

  adminService = inject(Admin)

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.adminService.getCategories()
      .subscribe({
        next: data => {
          this.categories = data;
        },
        error: error => {
          console.error('Failed to load categories', error);
        }
      });
  }
}
