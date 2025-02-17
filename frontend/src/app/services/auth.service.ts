import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthResponse } from '../models/authresponse';
import { LoginRequest } from '../models/loginrequest';

/**
 * Service handling authentication operations.
 * Manages user login, logout, and token storage.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  /** Base URL for authentication API endpoints */
  private readonly API_URL = environment.userApiUrl;
  
  /** Subject holding current user authentication state */
  private currentUserSubject: BehaviorSubject<AuthResponse | null>;
  
  /** Observable of the current user state */
  public currentUser: Observable<AuthResponse | null>;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.currentUserSubject = new BehaviorSubject<AuthResponse | null>(
      this.getUserFromStorage()
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  /**
   * Retrieves stored user data from localStorage.
   * @returns Parsed user data or null if not found
   */
  private getUserFromStorage(): AuthResponse | null {
    const storedUser = localStorage.getItem('currentUser');
    return storedUser ? JSON.parse(storedUser) : null;
  }

  /**
   * Gets the current user value.
   * @returns Current authenticated user or null
   */
  public get currentUserValue(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  /**
   * Authenticates user with provided credentials.
   * Stores authentication response in localStorage.
   * 
   * @param username User's username
   * @param password User's password
   * @returns Observable of authentication response
   */
  login(username: string, password: string): Observable<AuthResponse> {
    const loginData: LoginRequest = { username, password };
    console.log('Sending login request to:', `${this.API_URL}/api/auth/login`);
    console.log('Login data:', loginData);
    
    return this.http.post<AuthResponse>(`${this.API_URL}/api/auth/login`, loginData)
      .pipe(
        map(response => {
          console.log('Login response:', response);
          localStorage.setItem('currentUser', JSON.stringify(response));
          this.currentUserSubject.next(response);
          return response;
        })
      );
  }

  /**
   * Logs out the current user.
   * Removes stored authentication data and redirects to login.
   */
  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  /**
   * Checks if user is currently authenticated.
   * @returns true if user has valid token, false otherwise
   */
  isAuthenticated(): boolean {
    const currentUser = this.currentUserValue;
    return !!currentUser?.token;
  }

  /**
   * Gets the current authentication token.
   * @returns JWT token or null if not authenticated
   */
  getToken(): string | null {
    return this.currentUserValue?.token || null;
  }
}