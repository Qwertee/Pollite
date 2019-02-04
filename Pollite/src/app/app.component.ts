import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'pollite';
}

// TODO: Find a more "angulary" way to do this.
document.addEventListener('DOMContentLoaded', () => {
  let burger = document.getElementById('navbarBurger');
  burger.addEventListener('click', () => {

    // Get the target from the "data-target" attribute
    const target = burger.dataset.target;
    const $target = document.getElementById(target);

    // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
    burger.classList.toggle('is-active');
    $target.classList.toggle('is-active');

  });

});
