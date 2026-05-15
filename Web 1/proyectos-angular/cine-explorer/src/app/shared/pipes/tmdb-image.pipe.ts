// src/app/shared/pipes/tmdb-image.pipe.ts
// Pipe para construir la URL completa de imágenes de TMDB
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tmdbImage',
  standalone: true
})
export class TmdbImagePipe implements PipeTransform {
  private baseUrl = 'https://image.tmdb.org/t/p/';

  transform(path: string | null, size: string = 'w500'): string {
    if (!path) {
      return 'https://via.placeholder.com/500x750?text=Sin+imagen';
    }
    return `${this.baseUrl}${size}${path}`;
  }
}
