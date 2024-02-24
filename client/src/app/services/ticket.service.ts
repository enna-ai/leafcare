import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Ticket } from '../interfaces/ticket.interface';
import { environment } from '../../environments/environment';

type CreateTicketDto = {
  username: string;
  email: string;
  subject: string;
  description: string;
  images?: string[];
  category: string;
}

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  http = inject(HttpClient);

  getTickets() {
    return this.http.get<Ticket[]>(`${environment.apiBaseUrl}/tickets`);
  }

  createTicket(ticket: CreateTicketDto) {
    return this.http.post<Ticket>(
      `${environment.apiBaseUrl}/tickets`, {
        username: ticket.username,
        email: ticket.email,
        subject: ticket.subject,
        description: ticket.description,
        images: ticket.images,
        category: ticket.category,
      });
  }
}
