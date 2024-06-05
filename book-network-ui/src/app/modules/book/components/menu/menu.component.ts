import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit{

  ngOnInit(): void {
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {   // VERIFICA SI EL ENLACE SELECCIONADO CON TIENE href o '' Y AGREGA LA clase active
        link.classList.add('active');
      }
      link.addEventListener('click', () => {                                  // DEJA ACTIVO EL ENLACE SELECCIONADO, SELECINA LA CLASE .active  
         linkColor.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
      });
    });
  }

  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }
}
