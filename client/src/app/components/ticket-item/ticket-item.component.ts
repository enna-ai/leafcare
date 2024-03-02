import { CommonModule } from '@angular/common';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../interfaces/ticket.interface';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { formatDate, formatString } from '../../../utils';

@Component({
  selector: 'app-ticket-item',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './ticket-item.component.html',
  styleUrl: './ticket-item.component.scss'
})
export class TicketItemComponent implements OnInit {
  ticket: Ticket | undefined;
  response: string = '';

  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  @Output() responseSubmitted = new EventEmitter<{
    response: string,
  }>();

  ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
        const ticketId = params.get('id');
        if (ticketId) {
          this.getTicket(ticketId);
        }
      })
  }

  backButton() {
    this.router.navigate(["/tickets"]);
  }

  getTicket(ticketId: string): void {
    this.ticketService.viewTicket(ticketId).subscribe(ticket => this.ticket = ticket);
  }

  updateTicket(prop: string, value: string): void {
    const ticketId = this.route.snapshot.paramMap.get('id');
    if (!ticketId) {
      return;
    }

    this.ticketService.updateTicket(ticketId, prop, value).subscribe(ticket => this.ticket = ticket);
  }

  formatDate(date: Date | string) {
    return formatDate(date);
  }

  formatString(category: string) {
    return formatString(category);
  }

  responseSubmit(event: SubmitEvent) {
    event.preventDefault();

    const ticketId = this.route.snapshot.paramMap.get('id');
    if (!ticketId) {
      return;
    }

    const form = event.target as HTMLFormElement;
    const responseElement = form.elements.namedItem('ticket-response') as HTMLTextAreaElement;
    const response = responseElement.value;

    this.responseSubmitted.emit({
      response,
    });

    form.reset();

    this.ticketService.sendResponse(ticketId, response).subscribe(ticket => this.ticket = ticket);
  }
}
