/**
 * Interface representing a configuration entity.
 * Defines the structure of configuration objects in the system.
 */
export interface Configuration {
  /** Unique identifier for the configuration */
  id?: number;

  /** Unique key for accessing the configuration value */
  key: string;

  /** Value associated with the configuration key */
  value: string;

  /** Optional description of the configuration's purpose */
  description?: string;

  /** Username of the user who created the configuration */
  createdBy?: string;

  /** Username of the user who last modified the configuration */
  lastModifiedBy?: string;

  /** Timestamp when the configuration was created */
  createdAt?: Date;

  /** Timestamp when the configuration was last updated */
  updatedAt?: Date;
}