import { Component, HostListener, inject, OnInit } from '@angular/core';
import { User } from '../../service/user';
import { ProfileResponse } from '../../model/profile-response';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, RouterLink, RouterLinkActive, TranslatePipe],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit{
  constructor(
    private translate: TranslateService
  ) {}

  isScrolled = false;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 50) {
      this.isScrolled = true;
    } else {
      this.isScrolled = false;
    }
  }

  userService = inject(User);

  router = inject(Router);

  user!: ProfileResponse;

  ngOnInit(): void {
    const email = localStorage.getItem("email");

    if(email){
      this.userService.profile(email).subscribe({
        next: (response) => {
          this.user = response;
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  edit_profile(){
    this.router.navigate(["/edit-profile"]);   
  }

  logout(){
    localStorage.clear();
    this.router.navigate(["/"]);
  }

  watch_history(){
    this.router.navigate(['/watch-history'])
  }
}
