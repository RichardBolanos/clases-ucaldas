// src/app/core/services/favorites.service.ts
// Servicio para manejar películas favoritas con persistencia en localStorage
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Movie } from '../../models/movie';

@Injectable({ providedIn: 'root' })
export class FavoritesService {
  private readonly STORAGE_KEY = 'cine_favoritas';

  private favoritasSubject = new BehaviorSubject<Movie[]>(this.cargarDeStorage());

  // Observable público: los componentes se suscriben a esto
  favoritas$: Observable<Movie[]> = this.favoritasSubject.asObservable();

  // Observable de la cantidad (para el badge del navbar)
  cantidad$: Observable<number> = this.favoritas$.pipe(
    map(favs => favs.length)
  );

  agregar(movie: Movie): void {
    if (!this.esFavorita(movie.id)) {
      const nuevas = [...this.favoritasSubject.value, movie];
      this.favoritasSubject.next(nuevas);
      this.guardarEnStorage(nuevas);
    }
  }

  eliminar(id: number): void {
    const nuevas = this.favoritasSubject.value.filter(m => m.id !== id);
    this.favoritasSubject.next(nuevas);
    this.guardarEnStorage(nuevas);
  }

  toggle(movie: Movie): void {
    if (this.esFavorita(movie.id)) {
      this.eliminar(movie.id);
    } else {
      this.agregar(movie);
    }
  }

  esFavorita(id: number): boolean {
    return this.favoritasSubject.value.some(m => m.id === id);
  }

  obtenerTodas(): Movie[] {
    return this.favoritasSubject.value;
  }

  private cargarDeStorage(): Movie[] {
    try {
      const data = localStorage.getItem(this.STORAGE_KEY);
      return data ? JSON.parse(data) : [];
    } catch {
      return [];
    }
  }

  private guardarEnStorage(movies: Movie[]): void {
    try {
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(movies));
    } catch (error) {
      console.error('Error al guardar favoritas:', error);
    }
  }
}
