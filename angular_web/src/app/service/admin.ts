import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Info } from '../admin/admin-panel/admin-panel';

@Injectable({
  providedIn: 'root',
})
export class Admin {
  private http = inject(HttpClient);

  path = "http://localhost:8222/api/v1/catalog/admin";

  
  pathUser = "http://localhost:8222/api/v1/user/admin";

  uploadMovie(data: any) {
    return this.http.post<FormData>(
      `${this.path}/movie`, data
    );
  }

  addUser(data:any){
    return this.http.post<any>(
      `${this.pathUser}/add-user`, data
    );
  }

  editUser(data:any){
    return this.http.post<any>(
      `${this.pathUser}/edit-user`, data
    );
  }

  deleteUser(email: string){
    return this.http.delete(
      `${this.pathUser}/delete-user`,
      {
        body: { email: email }
      }
    );
  }

  getInfo(){
    return this.http.get<Info>(`${this.pathUser}/info`)
  }


  // movies

  getAllMovies(){
    return this.http.get<any>(
      `${this.path}/movies`
    );
  }

  editMovie(data:any){
    return this.http.post<any>(
      `${this.path}/edit-movie`, data
    );
  }

  deleteMovie(id: string){
    return this.http.delete(
      `${this.path}/delete-movie`,
      {
        body: { id: id }
      }
    );
  }

  getCategories(){
    return this.http.get<any>(
      `${this.path}/categories`
    );
  }

  getAnalytics(){
    return this.http.get<any>(
      `${this.path}/analytics`
    );
  }

}
