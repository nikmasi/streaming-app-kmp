import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthenticationResponse } from '../model/authentication-response';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  
  private http = inject(HttpClient);

  path = "http://localhost:8222/api/v1/auth";

  authenticate(data: any) {
    return this.http.post<AuthenticationResponse>(
      `${this.path}/authenticate`,data
    );
  }

  register(data: any) {
    return this.http.post<AuthenticationResponse>(
      `${this.path}/register`,data
    );
  }

  
}
