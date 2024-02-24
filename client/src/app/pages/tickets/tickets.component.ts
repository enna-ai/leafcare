import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';

@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule, TicketsComponent],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {
  ticketService = inject(TicketService);
  tickets = signal<Ticket[]>([]);

  ngOnInit(): void {
      this.getTickets();
  }

  getTickets() {
    this.ticketService.getTickets().subscribe((tickets) => this.tickets.set(tickets));
  }
}
