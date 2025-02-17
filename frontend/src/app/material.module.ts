import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

/**
 * Material Design module for the application.
 * Centralizes Angular Material imports and exports.
 * Includes modules for:
 * - Buttons and cards
 * - Form inputs
 * - Data tables with sorting and pagination
 * - Dialogs and notifications
 * - Icons and toolbar
 */
@NgModule({
  exports: [
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    MatSnackBarModule,
    MatIconModule,
    MatToolbarModule
  ]
})
export class MaterialModule { }