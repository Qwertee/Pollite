import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'pollite';

  ngOnInit(): void {
    document.addEventListener('DOMContentLoaded', () => {
      const burger = document.getElementById('navbarBurger');
      burger.addEventListener('click', () => {
        // Get the target from the "data-target" attribute
        const target = burger.dataset.target;
        const $target = document.getElementById(target);

        // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
        burger.classList.toggle('is-active');
        $target.classList.toggle('is-active');

      });

    });
  }
}
