import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Ticket } from '../interfaces/ticket.interface';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  http = inject(HttpClient);

  createTicket(subject: string, description: string, images: string[]) {
    return this.http.post<Ticket>(`${environment.apiBaseUrl}/tickets`, {
      subject,
      description,
      images,
    });
  }
}
