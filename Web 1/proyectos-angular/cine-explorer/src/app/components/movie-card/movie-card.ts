// movie-card.ts
// Componente de presentación reutilizable para mostrar una película
import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Movie } from '../../models/movie';
import { TruncatePipe } from '../../shared/pipes/truncate.pipe';
import { TmdbImagePipe } from '../../shared/pipes/tmdb-image.pipe';
import { StarsPipe } from '../../shared/pipes/stars.pipe';

@Component({
  selector: 'app-movie-card',
  standalone: true,
  imports: [RouterLink, TruncatePipe, TmdbImagePipe, StarsPipe],
  templateUrl: './movie-card.html',
  styleUrls: ['./movie-card.scss']
})
export class MovieCard {
  // Entrada: datos que recibe del padre
  movie = input.required<Movie>();
  esFavorita = input<boolean>(false);

  // Salida: evento que emite hacia el padre
  toggleFavorito = output<Movie>();

  onToggleFavorito(): void {
    this.toggleFavorito.emit(this.movie());
  }
}
