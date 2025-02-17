/**
 * Interface representing the authentication response from the server.
 * Contains the JWT token and authenticated user's username.
 */
export interface AuthResponse {
  /** JWT token for authenticated requests */
  token: string;
  /** Username of the authenticated user */
  username: string;
}