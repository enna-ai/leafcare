import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent implements OnInit {
  openSidebar: boolean = true;
  sidebarService = inject(SidebarService);

  menuSidebar = [
    {
      title: "Dashboard",
      link: "/dashboard",
      submenu: [],
    },
    {
      title: "Tickets",
      link: "/tickets",
      submenu: [
        {
          title: "All",
          link: "/tickets",
          value: null,
        },
        {
          title: "Open",
          link: null,
          value: "OPEN",
        },
        {
          title: "In Progress",
          link: null,
          value: "IN_PROGRESS",
        },
        {
          title: "Closed",
          link: null,
          value: "CLOSED",
        },
        {
          title: "Resolved",
          link: null,
          value: "RESOLVED",
        }
      ]
    },
    {
      title: "Category",
      link: null,
      submenu: [
        {
          title: "Hate Speech",
          link: null,
          value: "HATE_SPEECH",
        },
        {
          title: "Stalking",
          link: null,
          value: "STALKING",
        },
        {
          title: "Impersonation",
          link: null,
          value: "IMPERSONATION",
        },
        {
          title: "Cyberbullying",
          link: null,
          value: "CYBERBULLYING",
        },
        {
          title: "Threats Of Violence",
          link: null,
          value: "THREATS_OF_VIOLENCE",
        },
        {
          title: "Sexual Harassment",
          link: null,
          value: "SEXUAL_HARASSMENT",
        },
        {
          title: "Privacy Violation",
          link: null,
          value: "PRIVACY_VIOLATION",
        },
        {
          title: "Other",
          link: null,
          value: "OTHER",
        },
      ],
    }
  ];

  constructor() { }

  ngOnInit() { }

  showSubmenu(item: HTMLElement) {
    item.classList.toggle("showMenu")
  }

  selectItem(value: string | null) {
    this.sidebarService.setSelectedItem(value);
  }
}
