// src/app/core/services/tmdb.service.ts
// Servicio que centraliza todas las llamadas HTTP a la API de TMDB
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Movie,
  MovieResponse,
  MovieDetail,
  Credits,
  Genre
} from '../../models/movie';

@Injectable({ providedIn: 'root' })
export class TmdbService {
  private http = inject(HttpClient);
  private apiUrl = environment.tmdbBaseUrl;

  // GET /movie/popular
  obtenerPopulares(page: number = 1): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(`${this.apiUrl}/movie/popular`, {
      params: { language: 'es-ES', page: page.toString() }
    }).pipe(catchError(this.manejarError));
  }

  // GET /movie/top_rated
  obtenerMejorValoradas(page: number = 1): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(`${this.apiUrl}/movie/top_rated`, {
      params: { language: 'es-ES', page: page.toString() }
    }).pipe(catchError(this.manejarError));
  }

  // GET /movie/upcoming
  obtenerProximas(page: number = 1): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(`${this.apiUrl}/movie/upcoming`, {
      params: { language: 'es-ES', page: page.toString() }
    }).pipe(catchError(this.manejarError));
  }

  // GET /search/movie
  buscarPeliculas(query: string, page: number = 1): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(`${this.apiUrl}/search/movie`, {
      params: { query, language: 'es-ES', page: page.toString() }
    }).pipe(catchError(this.manejarError));
  }

  // GET /movie/{id}
  obtenerDetalle(id: number): Observable<MovieDetail> {
    return this.http.get<MovieDetail>(`${this.apiUrl}/movie/${id}`, {
      params: { language: 'es-ES' }
    }).pipe(catchError(this.manejarError));
  }

  // GET /movie/{id}/credits
  obtenerCreditos(id: number): Observable<Credits> {
    return this.http.get<Credits>(`${this.apiUrl}/movie/${id}/credits`, {
      params: { language: 'es-ES' }
    }).pipe(catchError(this.manejarError));
  }

  // GET /movie/{id}/similar
  obtenerSimilares(id: number): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(`${this.apiUrl}/movie/${id}/similar`, {
      params: { language: 'es-ES' }
    }).pipe(catchError(this.manejarError));
  }

  // GET /genre/movie/list
  obtenerGeneros(): Observable<{ genres: Genre[] }> {
    return this.http.get<{ genres: Genre[] }>(`${this.apiUrl}/genre/movie/list`, {
      params: { language: 'es-ES' }
    }).pipe(catchError(this.manejarError));
  }

  // GET /discover/movie?with_genres={id}
  obtenerPorGenero(genreId: number, page: number = 1): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(`${this.apiUrl}/discover/movie`, {
      params: { with_genres: genreId.toString(), language: 'es-ES', page: page.toString() }
    }).pipe(catchError(this.manejarError));
  }

  private manejarError(error: any): Observable<never> {
    let mensaje = 'Error desconocido';
    if (error.status === 0) {
      mensaje = 'Sin conexión a internet';
    } else if (error.status === 401) {
      mensaje = 'API key inválida';
    } else if (error.status === 404) {
      mensaje = 'Recurso no encontrado';
    } else {
      mensaje = `Error del servidor (${error.status})`;
    }
    return throwError(() => new Error(mensaje));
  }
}
