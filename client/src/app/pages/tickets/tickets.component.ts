import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, ViewChild } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { Router } from '@angular/router';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { SidebarService } from '../../services/sidebar.service';
import { MatPaginatorModule, MatPaginator, PageEvent } from '@angular/material/paginator';
import { formatDate, formatString } from '../../../utils';

@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule, SidebarComponent, MatPaginatorModule],
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sidebarService = inject(SidebarService);
  ticketService = inject(TicketService);
  tickets: Ticket[] = [];
  selectedTicket: string[] = [];
  filteredTickets: Ticket[] = [];
  pageSize: number = 10;

  constructor(private router: Router) {
    this.paginator = {} as MatPaginator;
  }

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
  }

  viewTicket(ticketId: string) {
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

    this.filteredTickets.sort((a, b) => {
      return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
    })

    // TODO: fix firstPage not a function error
    // if (this.paginator) {
    //   this.paginator.firstPage();
    // }
  }

  onPageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    this.filteredTickets = this.tickets.slice(startIndex, startIndex + event.pageSize);
  }

  formatDate(date: Date | string) {
    return formatDate(date);
  }

  formatString(category: string) {
    return formatString(category);
  }
}
