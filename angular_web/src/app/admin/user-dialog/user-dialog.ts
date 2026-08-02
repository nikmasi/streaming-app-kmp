import { Component, Inject } from '@angular/core';
import { 
  MAT_DIALOG_DATA, 
  MatDialogRef 
} from '@angular/material/dialog';

import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-user-dialog',
  imports: [
    FormsModule
  ],
  templateUrl: './user-dialog.html',
  styleUrl: './user-dialog.css'
})
export class UserDialog {
  user = {
    firstname: '',
    lastname: '',
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
    this.dialogRef.close(this.user);
  }

  close(){
    this.dialogRef.close();
  }
}