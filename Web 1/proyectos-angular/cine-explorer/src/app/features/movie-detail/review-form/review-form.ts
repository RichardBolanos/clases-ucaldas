// review-form.ts
// Componente de formulario de reseña usando Reactive Forms
import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-review-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './review-form.html'
})
export class ReviewForm {
  // Inyectar FormBuilder para crear el formulario de forma concisa
  private fb = inject(FormBuilder);

  // Formulario con validaciones
  formulario = this.fb.group({
    titulo: ['', [Validators.required, Validators.minLength(3)]],
    contenido: ['', [Validators.required, Validators.minLength(20)]],
    puntuacion: [5, [Validators.required, Validators.min(1), Validators.max(10)]],
    recomendada: [true]
  });

  // Estado del formulario
  enviado = false;

  // Helper para verificar errores de forma más limpia
  tieneError(campo: string, error: string): boolean {
    const control = this.formulario.get(campo);
    return !!control?.hasError(error) && !!control?.touched;
  }

  // Se ejecuta al enviar el formulario
  onSubmit(): void {
    if (this.formulario.valid) {
      console.log('Datos de la reseña:', this.formulario.value);
      this.enviado = true;
      // Resetear el formulario después de enviar
      this.formulario.reset({ puntuacion: 5, recomendada: true });
    }
  }
}
