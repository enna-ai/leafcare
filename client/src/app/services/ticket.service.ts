import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Ticket } from '../interfaces/ticket.interface';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

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

  viewTicket(ticketId: string): Observable<Ticket> {
    return this.http.get<Ticket>(`${environment.apiBaseUrl}/tickets/${ticketId}`);
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

  updateTicket(ticketId: string, prop: string, value: string): Observable<Ticket> {
    const body = { [prop]: value };
    return this.http.put<Ticket>(`${environment.apiBaseUrl}/tickets/${ticketId}`, body);
  }

  sendResponse(ticketId: string, res: string): Observable<Ticket> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const response = { body: res };

    return this.http.post<Ticket>(
      `${environment.apiBaseUrl}/tickets/${ticketId}/response`, response, {
      headers
    });
  }
}
