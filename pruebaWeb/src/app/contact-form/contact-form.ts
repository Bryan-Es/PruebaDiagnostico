import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { timeout, catchError, finalize } from 'rxjs/operators';
import { EMPTY } from 'rxjs';

@Component({
  selector: 'contact-form',
  standalone: true,
  imports: [CommonModule],
  template: `
    <form (submit)="$event.preventDefault()" class="contact-form">
      <div>
        <label for="name">Nombre</label>
        <input id="name" #nameInput placeholder="Ingresa tu nombre" />
      </div>
      <div>
        <label for="email">Correo</label>
        <input id="email" type="email" #emailInput placeholder="tu@ejemplo.com" />
      </div>
      <button type="button" (click)="submit(nameInput, emailInput)" [disabled]="isLoading">
        {{ 'Enviar' }}
      </button>
    </form>
    <p *ngIf="message" class="message">{{ message }}</p>
  `,
  styles: [
    `
      .contact-form {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;
        margin-top: 1rem;
      }
      .contact-form div {
        display: flex;
        flex-direction: column;
        gap: 0.25rem;
      }
      input {
        padding: 0.5rem;
        border-radius: 4px;
        border: 1px solid #ccc;
      }
      button {
        padding: 0.5rem 1rem;
        border-radius: 4px;
        border: 0;
        background: #007bff;
        color: white;
        cursor: pointer;
      }
      .message {
        margin-top: 0.75rem;
        font-weight: 600;
      }
    `,
  ],
})
export class ContactForm {
  message = '';
  isLoading = false;

  constructor(private readonly http: HttpClient) {}

  submit(nameInput: HTMLInputElement, emailInput: HTMLInputElement) {
    const trimmed = (nameInput?.value || '').trim();
    if (!trimmed) {
      this.message = 'Por favor ingresa tu nombre.';
      return;
    }
    const email = (emailInput?.value || '').trim();
    if (!email?.includes('@')) {
      this.message = 'Por favor ingresa un correo válido.';
      return;
    }

    this.isLoading = true;
    this.message = '';

    const payload = { nombre: trimmed, email };
    console.log('Enviando payload', payload);
    this.http
      .post('http://localhost:8080/api/usuarios', payload)
      .pipe(
        timeout(10000),
        catchError((err: any) => {
          console.error('Error en petición', err);
          this.message = 'Ocurrió un error al registrar.';
          return EMPTY;
        }),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe((res: any) => {
        console.log('Respuesta backend', res);
        const returnedName = res?.nombre ?? trimmed;
        const id = res?.id;
        this.message = id
          ? `Registro creado (id=${id}). Hola ${returnedName} gracias por registrarte`
          : `Hola ${returnedName} gracias por registrarte`;
        nameInput.value = '';
        emailInput.value = '';
        nameInput.focus();
      });
  }
}
