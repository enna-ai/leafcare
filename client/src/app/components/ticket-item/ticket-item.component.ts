import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { SidebarComponent } from '../sidebar/sidebar.component';
@Component({
  selector: 'app-ticket-item',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './ticket-item.component.html',
  styleUrl: './ticket-item.component.scss'
})
export class TicketItemComponent implements OnInit {
  ticket: Ticket | undefined;

  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
        const ticketId = params.get('id');
        if (ticketId) {
          this.getTicket(ticketId);
        }
      })
  }

  getTicket(ticketId: string): void {
    this.ticketService.viewTicket(ticketId).subscribe(ticket => this.ticket = ticket);
  }
}
