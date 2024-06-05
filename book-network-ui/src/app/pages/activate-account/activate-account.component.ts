import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { skipUntil } from 'rxjs';
import { AuthenticationService } from 'src/app/services/services';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent {

  message = '';                     // SIRVE PARA LA ACTIVACION O SI OCURRE UN ERROR EN EL MENSAJE
  isOkay = true;                    // PARA SABER SI LA CUENTA ESTA ACTIVADA O NO
  submitted = false;                // RASTREA SI EL USUARIO YA HA ENVIADO EL CODIGO DE ACTIVACION

  constructor(
    private router: Router,
    private authService: AuthenticationService        // SE VA A USAR EL endpoint /confirm DEL BACK END, USADO PARA ENVIAR EL CODIGO DE CONFIRMACION DEL CORREO AL BACKEND
  ) {}

  private confirmAccount(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account has been successfully activated.\nNow you can proceed to login';
        this.submitted = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid';
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  protected readonly skipUntil = skipUntil;
}
