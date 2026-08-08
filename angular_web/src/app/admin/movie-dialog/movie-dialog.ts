import { Component, Inject, inject } from '@angular/core';
import { Admin } from '../../service/admin';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-movie-dialog',
  imports: [CommonModule, FormsModule],
  templateUrl: './movie-dialog.html',
  styleUrl: './movie-dialog.css',
})
export class MovieDialog {

  adminService = inject(Admin);

  movie = {
    title: '',
    description: '',
    genres: [],
    duration: 0,
    releaseYear: 0,
    thumbnailUrl: '',
    videoUrl: ''
  };

  constructor(
    private dialogRef: MatDialogRef<MovieDialog>,
    @Inject(MAT_DIALOG_DATA) public data:any
  ){
    if(data){
      this.movie = {...data};
    }
  }

  save(){
    this.adminService.editMovie(this.movie).subscribe(response =>{})
    this.dialogRef.close(this.movie);
  }

  close(){
    this.dialogRef.close();
  }
}
