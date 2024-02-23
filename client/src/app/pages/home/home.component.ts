import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CreateFormComponent } from '../../components/create-form/create-form.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, CreateFormComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
