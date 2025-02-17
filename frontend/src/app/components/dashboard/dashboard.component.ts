import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfigService } from '../../services/config.service';
import { ConfigurationFormComponent } from '../configuration-form/configuration-form.component';
import { AuthService } from '../../services/auth.service';

/**
 * Dashboard component for managing configurations.
 * Provides a table interface with CRUD operations.
 */
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  displayedColumns: string[] = ['key', 'value', 'description', 'actions'];
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private configService: ConfigService,
    public authService: AuthService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.dataSource = new MatTableDataSource();
  }

  /**
   * Initializes component and loads configurations.
   */
  ngOnInit() {
    this.loadConfigurations();
  }

  /**
   * Loads configurations from the service.
   * Updates table data source with pagination and sorting.
   */
  loadConfigurations() {
    this.configService.getConfigurations().subscribe(data => {
      this.dataSource.data = data;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  /**
   * Opens dialog to add new configuration.
   * Updates table on successful creation.
   */
  addConfiguration() {
    const dialogRef = this.dialog.open(ConfigurationFormComponent, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.configService.createConfiguration(result).subscribe(() => {
          this.loadConfigurations();
          this.snackBar.open('Configuration created successfully', 'Close', {
            duration: 3000
          });
        });
      }
    });
  }

  /**
   * Opens dialog to edit existing configuration.
   * Updates table on successful edit.
   * 
   * @param config Configuration to edit
   */
  editConfiguration(config: any) {
    const dialogRef = this.dialog.open(ConfigurationFormComponent, {
      width: '500px',
      data: config
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.configService.updateConfiguration(config.id, result).subscribe(() => {
          this.loadConfigurations();
          this.snackBar.open('Configuration updated successfully', 'Close', {
            duration: 3000
          });
        });
      }
    });
  }

  /**
   * Deletes configuration after confirmation.
   * Updates table on successful deletion.
   * 
   * @param id ID of configuration to delete
   */
  deleteConfiguration(id: number) {
    if (confirm('Are you sure you want to delete this configuration?')) {
      this.configService.deleteConfiguration(id).subscribe(() => {
        this.loadConfigurations();
        this.snackBar.open('Configuration deleted successfully', 'Close', {
          duration: 3000
        });
      });
    }
  }

  /**
   * Applies filter to table data.
   * 
   * @param event Input event containing filter value
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}