// navbar.ts
// Componente de navegación con buscador y toggle de tema
import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { FavoritesService } from '../../core/services/favorites.service';
import { ThemeService } from '../../core/services/theme.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, FormsModule, AsyncPipe],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.scss']
})
export class Navbar {
  private router = inject(Router);
  private favoritesService = inject(FavoritesService);
  private themeService = inject(ThemeService);

  cantidad$ = this.favoritesService.cantidad$;
  terminoBusqueda: string = '';

  get temaActual(): string {
    return this.themeService.obtenerTema();
  }

  toggleTema(): void {
    this.themeService.toggle();
  }

  buscar(): void {
    const termino = this.terminoBusqueda.trim();
    if (termino) {
      this.router.navigate(['/search'], { queryParams: { q: termino } });
      this.terminoBusqueda = '';
    }
  }
}
