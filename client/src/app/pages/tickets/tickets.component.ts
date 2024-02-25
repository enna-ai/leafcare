import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { Router } from '@angular/router';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { SidebarService } from '../../services/sidebar.service';


@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {
  sidebarService = inject(SidebarService);
  ticketService = inject(TicketService);
  tickets: Ticket[] = [];
  selectedTicket: string[] = [];
  filteredTickets: Ticket[] = [];
  router = new Router();

  ngOnInit(): void {
    this.ticketService.getTickets().subscribe(tickets => {
      this.tickets = tickets;
      this.filterTickets();
    });

    this.sidebarService.selectedItem$.subscribe(value => {
      this.filterTickets();
    });
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

  filterTickets() {
    const selectedStatus = this.sidebarService.getSelectedItem();
    if (selectedStatus !== null) {
      if (!selectedStatus) {
        this.filteredTickets = [...this.tickets];
      } else {
        this.filteredTickets = this.tickets.filter(ticket => ticket.status === selectedStatus || ticket.category === selectedStatus);
      }
    } else {
      this.filteredTickets = [...this.tickets];
    }
  }
}
