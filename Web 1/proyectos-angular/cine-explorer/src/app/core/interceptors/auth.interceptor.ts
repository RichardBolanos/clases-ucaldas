// src/app/core/interceptors/auth.interceptor.ts
// Interceptor que agrega el token Bearer a las peticiones a TMDB
import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../../environments/environment';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Solo agregar token a peticiones dirigidas a TMDB
  if (req.url.includes('api.themoviedb.org')) {
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${environment.tmdbApiKey}`
      }
    });
    return next(clonedReq);
  }
  return next(req);
};
