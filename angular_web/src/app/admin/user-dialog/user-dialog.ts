import { Component, inject, Inject } from '@angular/core';
import { 
  MAT_DIALOG_DATA, 
  MatDialogRef 
} from '@angular/material/dialog';

import { FormsModule } from '@angular/forms';
import { Admin } from '../../service/admin';


@Component({
  selector: 'app-user-dialog',
  imports: [
    FormsModule
  ],
  templateUrl: './user-dialog.html',
  styleUrl: './user-dialog.css'
})
export class UserDialog {

  adminService = inject(Admin);

  user = {
    firstname: '',
    lastname: '',
    password: '',
    email: '',
    role: 'USER',
    profileImage: ''
  };

  constructor(
    private dialogRef: MatDialogRef<UserDialog>,
    @Inject(MAT_DIALOG_DATA) public data:any
  ){
    if(data){
      this.user = {...data};
    }
  }

  save(){
    if(!this.data){
      this.adminService.addUser(this.user).subscribe(response =>{
      })
    }
    else{
      this.adminService.editUser(this.user).subscribe(response =>{
      })
    }
    this.dialogRef.close(this.user);
  }

  close(){
    this.dialogRef.close();
  }
}