import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Configuration } from '../models/configuration';

/**
 * Service for managing configuration entities.
 * Provides CRUD operations for configurations using HTTP requests.
 */
@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  /** Base URL for configuration API endpoints */
  private apiUrl = `${environment.configApiUrl}/api/configs`;

  constructor(private http: HttpClient) { }

  /**
   * Retrieves all configurations from the server.
   * @returns Observable of configuration array
   */
  getConfigurations(): Observable<Configuration[]> {
    return this.http.get<Configuration[]>(this.apiUrl);
  }

  /**
   * Retrieves a specific configuration by ID.
   * @param id Configuration ID to retrieve
   * @returns Observable of single configuration
   */
  getConfiguration(id: number): Observable<Configuration> {
    return this.http.get<Configuration>(`${this.apiUrl}/${id}`);
  }

  /**
   * Creates a new configuration.
   * @param config Configuration data to create
   * @returns Observable of created configuration
   */
  createConfiguration(config: Configuration): Observable<Configuration> {
    return this.http.post<Configuration>(this.apiUrl, config);
  }

  /**
   * Updates an existing configuration.
   * @param id ID of configuration to update
   * @param config New configuration data
   * @returns Observable of updated configuration
   */
  updateConfiguration(id: number, config: Configuration): Observable<Configuration> {
    return this.http.put<Configuration>(`${this.apiUrl}/${id}`, config);
  }

  /**
   * Deletes a configuration.
   * @param id ID of configuration to delete
   * @returns Observable of void
   */
  deleteConfiguration(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}