import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthenticationResponse } from '../model/authentication-response';

import { jwtDecode } from 'jwt-decode';

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

  isAdmin(){
    const token = localStorage.getItem('access_token');

    if (!token) {
      return false;
    }

    const decoded: any = jwtDecode(token);
    return decoded.role?.includes('ADMIN');
  }

  isSignIn(){
    const token = localStorage.getItem('access_token');

    if (!token) {
      return false;
    }
    return true;
  }

}