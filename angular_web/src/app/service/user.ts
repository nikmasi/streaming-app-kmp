import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ProfileResponse } from '../model/profile-response';

@Injectable({
  providedIn: 'root',
})
export class User {
  private api = "http://localhost:8222/api/v1/user";

  private http = inject(HttpClient);

  profile(email: string){
    return this.http.post<ProfileResponse>(
      `${this.api}/profile`,
      {
        email: email
      }
    );
  }

  updateProfile(user: ProfileResponse){
    return this.http.post<ProfileResponse>(
      `${this.api}/edit-profile`,
      user
    );
  }

  getAllUsers(){
    return this.http.get<ProfileResponse[]>(
      `${this.api}/admin/users`
    );
  }
  
}
