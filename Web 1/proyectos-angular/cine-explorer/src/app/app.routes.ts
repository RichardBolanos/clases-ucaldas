// app.routes.ts
// Configuración de rutas de la aplicación
import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/home/home').then(m => m.Home)
  },
  {
    path: 'movie/:id',
    loadComponent: () =>
      import('./features/movie-detail/movie-detail').then(m => m.MovieDetailComponent)
  },
  {
    path: 'search',
    loadComponent: () =>
      import('./features/search-results/search-results').then(m => m.SearchResults)
  },
  {
    path: 'favorites',
    loadComponent: () =>
      import('./features/favorites/favorites').then(m => m.Favorites)
  },
  {
    path: 'genre/:id',
    loadComponent: () =>
      import('./features/genre-filter/genre-filter').then(m => m.GenreFilter)
  },
  { path: '**', redirectTo: '' }
];
