import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { Router } from '@angular/router';


@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {
  ticketService = inject(TicketService);
  tickets: Ticket[] = [];
  selectedTicket: string[] = [];
  router = new Router();

  ngOnInit(): void {
    this.ticketService.getTickets().subscribe(tickets => this.tickets = tickets);
  }

  onCheckboxChange(event: Event, ticketId: string) {
    const checkbox = event.target as HTMLInputElement;
    if (checkbox.checked) {
      this.selectedTicket.push(ticketId);
    } else {
      this.selectedTicket = this.selectedTicket.filter(id => id != ticketId);
    }

    console.log("Selected ticket ids:", this.selectedTicket);
  }

  viewTicket(ticketId: string) {
    console.log("Selected ID:", ticketId);
    this.router.navigate(["/tickets", ticketId]);
  }
}
