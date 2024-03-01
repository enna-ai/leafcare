import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  routes = [
    { path: '/terms', label: 'Terms of Service' },
    { path: '/policy', label: 'Privacy Policy' },
    { path: '/faq', label: 'FAQ' },
    { path: '/signin', label: 'Sign In' },
  ]
}
