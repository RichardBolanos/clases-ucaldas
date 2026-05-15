// src/app/core/services/theme.service.ts
// Servicio para manejar el tema claro/oscuro con persistencia
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly STORAGE_KEY = 'cine_tema';
  private temaActual: string;

  constructor() {
    this.temaActual = this.obtenerTemaInicial();
    this.aplicarTema(this.temaActual);
  }

  obtenerTema(): string {
    return this.temaActual;
  }

  cambiarTema(tema: string): void {
    this.temaActual = tema;
    this.aplicarTema(tema);
    localStorage.setItem(this.STORAGE_KEY, tema);
  }

  toggle(): void {
    const nuevoTema = this.temaActual === 'light' ? 'dark' : 'light';
    this.cambiarTema(nuevoTema);
  }

  private obtenerTemaInicial(): string {
    try {
      const guardado = localStorage.getItem(this.STORAGE_KEY);
      if (guardado) return guardado;
    } catch {}

    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return 'dark';
    }
    return 'light';
  }

  private aplicarTema(tema: string): void {
    document.documentElement.setAttribute('data-theme', tema);
  }
}
