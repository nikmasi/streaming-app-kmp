import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-not-found',
  imports: [TranslatePipe],
  templateUrl: './not-found.html',
  styleUrl: './not-found.css',
})
export class NotFound {
  constructor(
    private translate: TranslateService
  ) {}

  router = inject(Router)

  home(){
    this.router.navigate(["/home"])
  }
}
