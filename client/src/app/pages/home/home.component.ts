import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { CreateFormComponent } from '../../components/create-form/create-form.component';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { HeaderComponent } from '../../components/header/header.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, CreateFormComponent, HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  ticketService = inject(TicketService);
  tickets = signal<Ticket[]>([]);
  ticketSubmitted: boolean = false;

  ngOnInit() { }

  createTicket(formValues: { username: string, email: string, subject: string, description: string, images?: string[], category: string,  }) {
    const { username, email, subject, description, images, category } = formValues;

    this.ticketService
      .createTicket({
        username,
        email,
        subject,
        description,
        images,
        category
      }).subscribe({
        next: createdTicket => {
          this.ticketSubmitted = true;
          this.tickets.set([createdTicket, ...this.tickets()]);
        },
        error: error => {
          console.error("Error creating ticket:", error);
        }
      });
  }
}
