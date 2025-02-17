/**
 * Interface representing a login request.
 * Contains credentials needed for user authentication.
 */
export interface LoginRequest {
  /** Username for authentication */
  username: string;
  
  /** Password for authentication */
  password: string;
}