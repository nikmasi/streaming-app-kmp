import { Component, inject, OnInit } from '@angular/core';
import { User } from '../../service/user';
import { ProfileResponse } from '../../model/profile-response';
import { CommonModule } from '@angular/common';

import { MatDialog } from '@angular/material/dialog';
import { UserDialog } from '../user-dialog/user-dialog';
import { Admin } from '../../service/admin';
import { RouterLink, RouterLinkActive } from '@angular/router';


@Component({
  selector: 'app-admin-users',
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-users.html',
  styleUrl: './admin-users.css',
})
export class AdminUsers implements OnInit {

  private userService = inject(User);
  private adminService = inject(Admin);

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
        width: '450px',
        maxHeight: '90vh',
        position: {
          top: '50px'
        },
        data:user
    });

    dialogRef.afterClosed()
    .subscribe(result=>{
        if(result){
            console.log(result);
            this.ngOnInit()
        }
    });
  }

  addUser(){
    const dialogRef = this.dialog.open(UserDialog,{width:'450px',maxHeight: '90vh',});

    dialogRef.afterClosed().subscribe(result=>{
        if(result){
          console.log("new user",result);
          this.ngOnInit()
        }
    });
  }

  deleteUser(email: string){
    this.adminService.deleteUser(email).subscribe({
      next: () => { this.ngOnInit()},
      error: err => console.log(err)
    });
  }
}