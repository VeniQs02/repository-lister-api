import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RepositoryService {
  private baseUrl = 'http://localhost:8080/repository';
  private username = '123'; //replace with actual username
  private password = '123'; //replace with actual password

  constructor(private http: HttpClient) {}

  public getRepositories(username: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(`${this.username}:${this.password}`)
    });
    return this.http.get(`${this.baseUrl}/${username}`, { headers });
  }
}
