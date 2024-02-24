import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { TicketComponent } from '../../components/ticket/ticket.component';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';


@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule, TicketComponent],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {
  ticketService = inject(TicketService);
  tickets: Ticket[] = [];

  ngOnInit(): void {
    this.ticketService.getTickets().subscribe(tickets => this.tickets = tickets);
  }
}
