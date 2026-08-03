import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-upload-video',
  imports: [FormsModule],
  templateUrl: './admin-upload-video.html',
  styleUrl: './admin-upload-video.css',
})
export class AdminUploadVideo {
  movie = {
    title: '',
    description: '',
    releaseYear: new Date().getFullYear(),
    duration: 120
  };

  genres = '';
  thumbnail?: File;
  video?: File;

  onThumbnailSelected(event: any){
    this.thumbnail = event.target.files[0];
  }

  onVideoSelected(event: any){
      this.video = event.target.files[0];
  }

  uploadMovie(){
    const formData = new FormData();
    formData.append("title", this.movie.title);
    formData.append("description", this.movie.description);
    formData.append("duration", this.movie.duration.toString());
    formData.append("releaseYear", this.movie.releaseYear.toString());

    formData.append(
        "genres",
        JSON.stringify(
            this.genres
                .split(',')
                .map(g => g.trim())
        )
    );

    if(this.thumbnail){
      formData.append("thumbnail", this.thumbnail);
    }

    if(this.video){
      formData.append("video", this.video);
    }

    // this.movieService.uploadMovie(formData)
    //      .subscribe(...)
  }
}
