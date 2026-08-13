import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-sign-up',
  imports: [FormsModule, CommonModule, TranslatePipe],
  templateUrl: './sign-up.html',
  styleUrl: './sign-up.css',
})
export class SignUp {
  constructor(
    private translate: TranslateService
  ) {}

  changeLanguage(language: string) {
    this.translate.use(language);
  }

  router = inject(Router)

  email = '';

  sign_in(){
    this.router.navigate(['/signIn'])
  }

  sign_up(){ 
    this.router.navigate(['/sign-up-name'], {
      queryParams: {
        email: this.email
      }
    });
  }

  openedFaq: number | null = null;

  toggleFaq(index: number) {
    this.openedFaq =
        this.openedFaq === index
            ? null
            : index;
}
}
