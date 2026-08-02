import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { User } from '../../service/user';
import { ProfileResponse } from '../../model/profile-response';

@Component({
  selector: 'app-edit-profile',
  standalone: true,
  imports: [ FormsModule, RouterLink ],
  templateUrl: './edit-profile.html',
  styleUrl: './edit-profile.css',
})
export class EditProfile implements OnInit {
  userService = inject(User);
  router = inject(Router);

  user: ProfileResponse = {
    firstname: '',
    lastname: '',
    email: '',
    role: '',
    profileImage: ''
  };


  ngOnInit(): void {
    const email = localStorage.getItem("email");

    if(email){
      this.userService.profile(email)
        .subscribe({
          next:(response)=>{
            this.user = response;
          },
          error:(error)=>{
            console.log(error);
          }
        });
    }
  }

  save(){
    this.userService.updateProfile(this.user)
      .subscribe({
        next:(response)=>{
          this.user = response;

          this.router.navigate(['/profile']);
        },
        error:(error)=>{
          console.log(error);
        }
      });
  }
}