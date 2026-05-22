// navbar.ts
// Navbar con buscador reactivo que usa debounce (capítulo 7)
import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { debounceTime, distinctUntilChanged, filter } from 'rxjs';
import { FavoritesService } from '../../core/services/favorites.service';
import { ThemeService } from '../../core/services/theme.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, ReactiveFormsModule, AsyncPipe],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.scss']
})
export class Navbar {
  private router = inject(Router);
  private favoritesService = inject(FavoritesService);
  private themeService = inject(ThemeService);

  // Observable de cantidad de favoritas (para el badge)
  cantidad$ = this.favoritesService.cantidad$;

  // FormControl para el input de búsqueda
  // Cada vez que el usuario escribe, emite el nuevo valor como Observable
  searchControl = new FormControl('');

  get temaActual(): string {
    return this.themeService.obtenerTema();
  }

  constructor() {
    // valueChanges es un Observable que emite cada vez que el input cambia
    this.searchControl.valueChanges.pipe(
      // debounceTime(300): espera 300ms después del último tecleo
      // Si el usuario sigue escribiendo, reinicia el timer
      debounceTime(300),
      // distinctUntilChanged: solo emite si el valor es diferente al anterior
      // Evita peticiones duplicadas si el usuario borra y reescribe lo mismo
      distinctUntilChanged(),
      // filter: solo emite si el texto tiene 2+ caracteres
      // Evita buscar con textos muy cortos
      filter(term => !!term && term.length >= 2)
    ).subscribe(term => {
      // Navegar a la página de resultados con el término como query param
      this.router.navigate(['/search'], { queryParams: { q: term } });
    });
  }

  toggleTema(): void {
    this.themeService.toggle();
  }

  // Búsqueda manual al presionar Enter o click en el botón
  buscar(): void {
    const termino = (this.searchControl.value || '').trim();
    if (termino) {
      this.router.navigate(['/search'], { queryParams: { q: termino } });
      this.searchControl.setValue('');
    }
  }
}
