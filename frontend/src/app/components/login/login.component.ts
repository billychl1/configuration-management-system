import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Component handling user authentication.
 * Provides login form and authentication logic.
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;
  returnUrl: string = '/dashboard';
  errorMessage: string = ''; // Add this line for error message

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
    // redirect to dashboard if already logged in
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/dashboard']);
    }
  }

  /**
   * Initializes login form and gets return URL from route parameters.
   */
  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    // get return url from route parameters or default to '/dashboard'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/dashboard';
  }

  /**
   * Handles form submission.
   * Attempts to authenticate user with provided credentials.
   */
  onSubmit() {
    this.errorMessage = ''; // Clear previous error
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    const { username, password } = this.loginForm.value;
    
    this.authService.login(username, password)
      .subscribe({
        next: () => {
          this.router.navigate([this.returnUrl]);
        },
        error: error => {
          this.errorMessage = error.error?.message || 'Invalid username or password';
          this.snackBar.open(this.errorMessage, 'Close', {
            duration: 5000,
            panelClass: ['error-snackbar'],
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.loading = false;
        }
      });
  }
}