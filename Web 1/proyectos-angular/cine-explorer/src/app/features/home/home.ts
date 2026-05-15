// home.ts
// Página principal: muestra películas populares, mejor valoradas y próximas
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { MovieCard } from '../../components/movie-card/movie-card';
import { TmdbService } from '../../core/services/tmdb.service';
import { FavoritesService } from '../../core/services/favorites.service';
import { Movie } from '../../models/movie';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MovieCard],
  templateUrl: './home.html',
  styleUrls: ['./home.scss']
})
export class Home implements OnInit {
  private tmdbService = inject(TmdbService);
  private favoritesService = inject(FavoritesService);
  private cdr = inject(ChangeDetectorRef);

  populares: Movie[] = [];
  mejorValoradas: Movie[] = [];
  proximas: Movie[] = [];

  cargando = true;
  error = '';

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.cargando = true;
    this.error = '';

    this.tmdbService.obtenerPopulares().subscribe({
      next: (data) => {
        this.populares = data.results.slice(0, 6);
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.error = err.message;
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });

    this.tmdbService.obtenerMejorValoradas().subscribe({
      next: (data) => {
        this.mejorValoradas = data.results.slice(0, 6);
        this.cdr.markForCheck();
      },
      error: () => {}
    });

    this.tmdbService.obtenerProximas().subscribe({
      next: (data) => {
        this.proximas = data.results.slice(0, 6);
        this.cdr.markForCheck();
      },
      error: () => {}
    });
  }

  esFavorita(id: number): boolean {
    return this.favoritesService.esFavorita(id);
  }

  toggleFavorito(movie: Movie): void {
    this.favoritesService.toggle(movie);
  }
}
