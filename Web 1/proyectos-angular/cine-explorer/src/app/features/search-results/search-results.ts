// search-results.ts
// Página de resultados de búsqueda con paginación
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TmdbService } from '../../core/services/tmdb.service';
import { FavoritesService } from '../../core/services/favorites.service';
import { MovieCard } from '../../components/movie-card/movie-card';
import { Movie, MovieResponse } from '../../models/movie';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [MovieCard],
  templateUrl: './search-results.html'
})
export class SearchResults implements OnInit {
  private route = inject(ActivatedRoute);
  private tmdbService = inject(TmdbService);
  private favoritesService = inject(FavoritesService);
  private cdr = inject(ChangeDetectorRef);

  resultados: Movie[] = [];
  termino = '';
  paginaActual = 1;
  totalPaginas = 0;
  totalResultados = 0;
  cargando = false;
  error = '';

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.termino = params['q'] || '';
      this.paginaActual = +(params['page'] || 1);
      if (this.termino) {
        this.buscar();
      }
    });
  }

  buscar(): void {
    this.cargando = true;
    this.error = '';

    this.tmdbService.buscarPeliculas(this.termino, this.paginaActual).subscribe({
      next: (data: MovieResponse) => {
        this.resultados = data.results;
        this.totalPaginas = data.total_pages;
        this.totalResultados = data.total_results;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.error = err.message;
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }

  cambiarPagina(pagina: number): void {
    this.paginaActual = pagina;
    this.buscar();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  esFavorita(id: number): boolean {
    return this.favoritesService.esFavorita(id);
  }

  toggleFavorito(movie: Movie): void {
    this.favoritesService.toggle(movie);
  }
}
