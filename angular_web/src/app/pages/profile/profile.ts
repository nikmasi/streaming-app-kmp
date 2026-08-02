import { Component, inject, OnInit } from '@angular/core';
import { User } from '../../service/user';
import { ProfileResponse } from '../../model/profile-response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit{

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

}
