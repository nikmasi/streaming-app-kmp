import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Admin {
  private http = inject(HttpClient);

  path = "http://localhost:8222/api/v1/catalog/admin";

  uploadMovie(data: any) {
    return this.http.post<FormData>(
      `${this.path}/movie`, data
    );
  }
}
