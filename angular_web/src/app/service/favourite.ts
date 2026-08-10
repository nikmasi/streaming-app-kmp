import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContentType, PreferenceRequest, PreferenceStatus, UserContentPreference } from '../model/favourite';



@Injectable({
  providedIn: 'root',
})
export class Favourite {
  private http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8222/api/v1/favourites';

  getAll(userId: string): Observable<UserContentPreference[]> {
    return this.http.get<UserContentPreference[]>(
      `${this.apiUrl}/${userId}`
    );
  }

  getByStatus(userId: string, status: PreferenceStatus
  ): Observable<UserContentPreference[]> {
    return this.http.get<UserContentPreference[]>(
      `${this.apiUrl}/${userId}/status/${status}`
    );
  }

  getPreference(userId: string, contentType: ContentType, contentId: string): Observable<UserContentPreference> {
    return this.http.get<UserContentPreference>(
      `${this.apiUrl}/${userId}/${contentType}/${contentId}`
    );
  }

  setPreference(userId: string, request: PreferenceRequest): Observable<UserContentPreference> {
    return this.http.put<UserContentPreference>(
      `${this.apiUrl}/${userId}`,
      request
    );
  }

  removePreference(userId: string, contentType: ContentType, contentId: string): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${userId}/${contentType}/${contentId}`
    );
  }
}