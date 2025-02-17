import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';

/**
 * Application routing configuration.
 * Defines routes and their access restrictions:
 * - /login: Public access to login page
 * - /dashboard: Protected by AuthGuard
 * - Default route redirects to dashboard
 * - Wildcard route catches invalid URLs
 */
const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: '/dashboard' }
];

/**
 * Routing module for the application.
 * Configures routes and exports RouterModule for use in AppModule.
 */
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }