// genre-filter.ts
// Página que muestra películas filtradas por género
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TmdbService } from '../../core/services/tmdb.service';
import { FavoritesService } from '../../core/services/favorites.service';
import { MovieCard } from '../../components/movie-card/movie-card';
import { Movie, Genre } from '../../models/movie';

@Component({
  selector: 'app-genre-filter',
  standalone: true,
  imports: [MovieCard],
  templateUrl: './genre-filter.html'
})
export class GenreFilter implements OnInit {
  private route = inject(ActivatedRoute);
  private tmdbService = inject(TmdbService);
  private favoritesService = inject(FavoritesService);

  peliculas: Movie[] = [];
  generoActual: Genre | null = null;
  generos: Genre[] = [];
  paginaActual = 1;
  totalPaginas = 0;
  cargando = true;
  error = '';

  ngOnInit(): void {
    // Cargar lista de géneros
    this.tmdbService.obtenerGeneros().subscribe({
      next: (data) => this.generos = data.genres,
      error: () => {}
    });

    // Reaccionar a cambios de parámetro
    this.route.params.subscribe(params => {
      const genreId = +params['id'];
      this.paginaActual = 1;
      this.cargarPorGenero(genreId);
    });
  }

  cargarPorGenero(genreId: number): void {
    this.cargando = true;
    this.error = '';

    this.tmdbService.obtenerPorGenero(genreId, this.paginaActual).subscribe({
      next: (data) => {
        this.peliculas = data.results;
        this.totalPaginas = data.total_pages;
        this.generoActual = this.generos.find(g => g.id === genreId) || { id: genreId, name: 'Género' };
        this.cargando = false;
      },
      error: (err) => {
        this.error = err.message;
        this.cargando = false;
      }
    });
  }

  cambiarPagina(pagina: number): void {
    this.paginaActual = pagina;
    if (this.generoActual) {
      this.cargarPorGenero(this.generoActual.id);
    }
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  esFavorita(id: number): boolean {
    return this.favoritesService.esFavorita(id);
  }

  toggleFavorito(movie: Movie): void {
    this.favoritesService.toggle(movie);
  }
}
