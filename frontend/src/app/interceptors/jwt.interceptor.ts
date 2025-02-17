import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * HTTP interceptor that adds JWT authentication token to requests.
 * Automatically adds Authorization header with Bearer token if user is authenticated.
 */
@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }

  /**
   * Intercepts HTTP requests and adds JWT token to headers.
   * Only adds token if user is authenticated and token exists.
   *
   * @param request The outgoing HTTP request
   * @param next The next interceptor in chain
   * @returns Observable of the HTTP event
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const currentUser = this.authService.currentUserValue;
    if (currentUser && currentUser.token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${currentUser.token}`
        }
      });
    }

    return next.handle(request);
  }
}