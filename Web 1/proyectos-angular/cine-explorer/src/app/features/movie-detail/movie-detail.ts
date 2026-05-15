// movie-detail.ts
// Página de detalle de una película: info completa, créditos y similares
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TmdbService } from '../../core/services/tmdb.service';
import { FavoritesService } from '../../core/services/favorites.service';
import { TmdbImagePipe } from '../../shared/pipes/tmdb-image.pipe';
import { TruncatePipe } from '../../shared/pipes/truncate.pipe';
import { MovieCard } from '../../components/movie-card/movie-card';
import { MovieDetail, Credits, Movie } from '../../models/movie';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-movie-detail',
  standalone: true,
  imports: [TmdbImagePipe, TruncatePipe, MovieCard, RouterLink, DecimalPipe],
  templateUrl: './movie-detail.html',
  styleUrls: ['./movie-detail.scss']
})
export class MovieDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private tmdbService = inject(TmdbService);
  private favoritesService = inject(FavoritesService);

  pelicula: MovieDetail | null = null;
  creditos: Credits | null = null;
  similares: Movie[] = [];
  cargando = true;
  error = '';

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.cargarPelicula(+params['id']);
    });
  }

  cargarPelicula(id: number): void {
    this.cargando = true;
    this.error = '';
    this.pelicula = null;
    this.creditos = null;
    this.similares = [];

    this.tmdbService.obtenerDetalle(id).subscribe({
      next: (data) => {
        this.pelicula = data;
        this.cargando = false;
      },
      error: (err) => {
        this.error = err.message;
        this.cargando = false;
      }
    });

    this.tmdbService.obtenerCreditos(id).subscribe({
      next: (data) => this.creditos = data,
      error: () => {}
    });

    this.tmdbService.obtenerSimilares(id).subscribe({
      next: (data) => this.similares = data.results.slice(0, 6),
      error: () => {}
    });
  }

  get esFavorita(): boolean {
    return this.pelicula ? this.favoritesService.esFavorita(this.pelicula.id) : false;
  }

  toggleFavorito(): void {
    if (this.pelicula) {
      this.favoritesService.toggle(this.pelicula as Movie);
    }
  }

  toggleFavoritoSimilar(movie: Movie): void {
    this.favoritesService.toggle(movie);
  }

  esFavoritaSimilar(id: number): boolean {
    return this.favoritesService.esFavorita(id);
  }
}
