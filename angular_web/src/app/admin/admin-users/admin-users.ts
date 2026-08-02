import { Component, inject, OnInit } from '@angular/core';
import { User } from '../../service/user';
import { ProfileResponse } from '../../model/profile-response';
import { CommonModule } from '@angular/common';

import { MatDialog } from '@angular/material/dialog';
import { UserDialog } from '../user-dialog/user-dialog';


@Component({
  selector: 'app-admin-users',
  imports: [CommonModule],
  templateUrl: './admin-users.html',
  styleUrl: './admin-users.css',
})
export class AdminUsers implements OnInit {

  private userService = inject(User);
  users: ProfileResponse[] = [];

  private dialog = inject(MatDialog);


  ngOnInit(): void {

    this.userService.getAllUsers()
      .subscribe({
        next: (response) => {
          this.users = response;
        },

        error: (error) => {
          console.log(error);
        }
      });
  }

  editUser(user: ProfileResponse){
    const dialogRef = this.dialog.open(UserDialog,{
        width:'700px',
        data:user
    });

    dialogRef.afterClosed()
    .subscribe(result=>{
        if(result){
            console.log(result);
        }
    });
  }

  addUser(){
    const dialogRef = this.dialog.open(UserDialog,{width:'500px'});

    dialogRef.afterClosed().subscribe(result=>{
        if(result){
          console.log("new user",result);
          // POST /admin/users
        }
    });
  }

}