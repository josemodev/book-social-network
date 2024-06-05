import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {
  }

  login() {
    this.errorMsg = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        this.tokenService.token = res.token as string;
        this.router.navigate(['books']);
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {               // SIEMPRE IRA error DESPUES DEL VALOR ANTES DEL lambda, Y LUEGO LA PROP QUE SE ESPERA
          this.errorMsg = err.error.validationErrors;   // validationErrors ES UN CAMPO DE UN json Y ES DE TIPO ARRAY.
        } else {
          this.errorMsg.push(err.error.error);          // error ES UN CAMPO DE UN OBJETO json. 
        }
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
