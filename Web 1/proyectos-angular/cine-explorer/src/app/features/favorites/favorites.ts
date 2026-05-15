// favorites.ts
// Página de películas favoritas guardadas en localStorage
import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { FavoritesService } from '../../core/services/favorites.service';
import { MovieCard } from '../../components/movie-card/movie-card';
import { Movie } from '../../models/movie';

@Component({
  selector: 'app-favorites',
  standalone: true,
  imports: [MovieCard, AsyncPipe],
  templateUrl: './favorites.html'
})
export class Favorites {
  private favoritesService = inject(FavoritesService);

  favoritas$ = this.favoritesService.favoritas$;

  esFavorita(id: number): boolean {
    return this.favoritesService.esFavorita(id);
  }

  toggleFavorito(movie: Movie): void {
    this.favoritesService.toggle(movie);
  }
}
