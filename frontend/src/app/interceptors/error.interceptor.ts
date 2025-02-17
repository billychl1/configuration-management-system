import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

/**
 * HTTP interceptor that handles error responses.
 * Automatically handles authentication errors and logs out user when 401 status is received.
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService) { }

    /**
     * Intercepts HTTP requests and handles error responses.
     * Logs out user on 401 Unauthorized response.
     * 
     * @param request The outgoing HTTP request
     * @param next The next interceptor in chain
     * @returns Observable of the HTTP event
     */
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401) {
                // Auto logout if 401 Unauthorized response returned from API
                this.authService.logout();
            }
            return throwError(() => err);
        }));
    }
}