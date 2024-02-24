import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-create-form',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-form.component.html',
  styleUrl: './create-form.component.scss'
})
export class CreateFormComponent {
  email: string = '';
  username: string = '';
  category: string = '';
  subject: string = '';
  description: string = '';
  images?: string[] = [];

  @Output() formSubmitted = new EventEmitter<{
    email: string,
    username: string,
    category: string,
    subject: string,
    description: string,
    images?: string[],
  }>();

  formSubmit(event: SubmitEvent) {
    event.preventDefault();

    const form = event.target as HTMLFormElement;
    const emailElement = form.elements.namedItem('email') as HTMLInputElement;
    const usernameElement = form.elements.namedItem('username') as HTMLInputElement;
    const textAreaElement = form.elements.namedItem('description') as HTMLTextAreaElement;
    const optionElement = form.elements.namedItem('category') as HTMLOptionElement;
    const subjectElement = form.elements.namedItem('subject') as HTMLInputElement;
    const imagesElement = form.elements.namedItem('attachments') as HTMLInputElement;

    const email = emailElement.value;
    const username = usernameElement.value;
    const description = textAreaElement.value;
    const category = optionElement.value;
    const subject = subjectElement.value;
    const files = imagesElement.files;

    const images: string[] = [];

    if (files) {
      for (let i = 0; i < files?.length; i++) {
        const file = files[i];
        const img = URL.createObjectURL(file);
        images.push(img);
      }
    }

    this.formSubmitted.emit({
      username,
      email,
      subject,
      description,
      images,
      category,
    });

    form.reset();
  }
}
