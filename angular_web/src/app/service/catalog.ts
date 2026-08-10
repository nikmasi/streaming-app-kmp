import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MovieResponse } from './playback';

@Injectable({
  providedIn: 'root',
})
export class Catalog {

  private http = inject(HttpClient);

  path = "http://localhost:8222/api/v1/catalog";

  home() {
    return this.http.get(`${this.path}/home`);
  }

  yearTop5(){
    return this.http.get(`${this.path}/yearTop5`);
  }

  search(query:string){
    return this.http.get(
      `${this.path}/search?title=${query}&email=${'aa'}`
    );
  }

  getMovie(id:string){
    return this.http.get<MovieResponse>(
      `${this.path}/movie/${id}`
    );
  }

}
