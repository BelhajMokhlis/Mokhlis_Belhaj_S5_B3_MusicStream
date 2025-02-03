import { HttpInterceptorFn } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';

interface JwtPayloadCustom {
  roles: Array<{authority: string}>;
  // other claims...
}

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const apiUrlPattern = 'api/users';
  const apiUrlPattern2 = 'api/admin';
  if (req.url.includes(apiUrlPattern2)){
    const token = localStorage.getItem('token');
    if (!token) {
      return next(req);
    }
    const decodedToken = jwtDecode<JwtPayloadCustom>(token);
    const isAdmin = decodedToken.roles.some(role => role.authority === 'ROLE_ADMIN');
    if (!isAdmin) {
      return next(req);
    }
  }
  if (req.url.includes(apiUrlPattern) || req.url.includes(apiUrlPattern2)) {
    const token = localStorage.getItem('token');
    if (token) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next(cloned);
    }
  }
  return next(req);
};
