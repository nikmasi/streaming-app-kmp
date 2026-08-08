import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Admin } from '../../service/admin';
import { Movie } from '../../model/movie';
import { MovieDialog } from '../movie-dialog/movie-dialog';
import { MatDialog } from '@angular/material/dialog';
import { environment } from '../../pages/home/home';

export interface MovieResponse{
  id: string;
  title: string,
  description: string,
  genres: string[],
  duration: Number,
  releaseYear: Number,
  thumbnailUrl: string,
  videoUrl: string,
}

@Component({
  selector: 'app-admin-movies',
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-movies.html',
  styleUrl: './admin-movies.css',
})
export class AdminMovies implements OnInit{

  adminService = inject(Admin);

  movies: MovieResponse[] = []

  router = inject(Router);

  private dialog = inject(MatDialog);

  ngOnInit(): void {
    this.adminService.getAllMovies().subscribe(response =>{
      this.movies= response;
    })
  }

  getThumbnailUrl(thumbnailUrl: string): string {
    return `${environment.apiUrl}/api/v1/catalog/${thumbnailUrl}`;
  }

  addMovie(){
    this.router.navigate(["/admin-upload-video"])
  }

  editMovie(movie:MovieResponse){
    const dialogRef = this.dialog.open(MovieDialog,{
        width: '450px',
        maxHeight: '90vh',
        position: { top: '50px' },
        data:movie
    });
    
    dialogRef.afterClosed().subscribe(result=>{
        if(result){
          this.ngOnInit()
        }
    });
  }

  deleteMovie(id:string){
    this.adminService.deleteMovie(id).subscribe(response=>{
      this.ngOnInit()
    })
  }
}
