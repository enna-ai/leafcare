import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { CreateFormComponent } from '../create-form/create-form.component';
import { Ticket } from '../../interfaces/ticket.interface';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-ticket-item',
  standalone: true,
  imports: [CommonModule, CreateFormComponent],
  templateUrl: './ticket-item.component.html',
  styleUrl: './ticket-item.component.scss'
})
export class TicketItemComponent {
  // @Input() ticket!: Ticket;
  // ticketService = inject(TicketService);

  // createTicket(formValues: { email: string, username: string, category: string, subject: string, description: string, images?: string[] }) {
  //   const { email, username, category, subject, description, images } = formValues;

  //   this.ticketService
  //     .createTicket({
  //       email,
  //       username,
  //       category,
  //       subject,
  //       description,
  //       images,
  //     });
  // }
}
