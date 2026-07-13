import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UserRequest, UserResponse } from '../models/customer.model';
import { LoginResponse } from '../models/auth.model';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'jwtToken';

  private userSubject = new BehaviorSubject<UserResponse | null>(null);
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    const token = localStorage.getItem(this.tokenKey);
    if (token) {
      const user = this.parseUserFromToken(token);
      if (user) {
        this.userSubject.next(user);
      } else {
        this.logOut();
      }
    }
  }

  register(data: UserRequest) {
    return this.http.post(`${this.apiUrl}/signup`, data, {
      responseType: 'text',
    });
  }

  private parseUserFromToken(token: string): UserResponse | null {
    try {
      const base64 = token.split('.')[1];
      const decode = atob(base64);
      const data = JSON.parse(decode);

      return {
        userName: data.sub,
        userFirstName: data.userFirstName || '',
        userLastName: data.userLastName || '',
        email: data.email || '',
        roles: (data.roles || []).map((r: any) => ({
          roleName: typeof r === 'string' ? r : r.roleName,
        })),
      };
    } catch (error) {
      console.error('Failed to parse token', error);
      return null;
    }
  }

  login(username: string, password: string) {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/signin`, { username, password })
      .pipe(
        tap((res) => {
          localStorage.setItem(this.tokenKey, res.jwtToken);
          const user = this.parseUserFromToken(res.jwtToken);

          if (!user && res.user) {
            this.userSubject.next({
              userName: res.user.userName,
              userFirstName: res.user.userFirstName,
              userLastName: res.user.userLastName,
              email: res.user.email,
              roles: res.user.roles.map((r) => ({ roleName: r.roleName })),
            });
          } else {
            this.userSubject.next(user);
          }
        })
      );
  }

  logOut() {
    localStorage.removeItem(this.tokenKey);
    this.userSubject.next(null);
  }
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): UserResponse | null {
    return this.userSubject.value;
  }

  hasRole(role: string): boolean {
    return (

      this.userSubject.value?.roles?.some((r) =>{
        // console.log('--Role-------:', r.roleName,'---------:' ,role  );
        return r.roleName === role 
      } ) || false
    );
  }
}
