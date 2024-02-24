export interface Ticket {
  id: string;
  username: string;
  email: string;
  subject: string;
  description: string;
  images?: string[];
  category: string;
  status: string;
  priority: string;
  createdAt: Date | string;
}
