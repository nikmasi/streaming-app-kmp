import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Admin } from '../../service/admin';


export interface Info{
  movieNumber:Number,
  tvShowNumber:Number,
  userNumber:Number
}

@Component({
  selector: 'app-admin-panel',
  imports: [ CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-panel.html',
  styleUrl: './admin-panel.css',
})
export class AdminPanel implements OnInit{

  adminService =inject(Admin)

  router = inject(Router)

  info: Info =  {
    movieNumber:0,
    tvShowNumber:0,
    userNumber:0
  }

  ngOnInit(): void {
    this.getInfo()
  }

  getInfo(){
    this.adminService.getInfo().subscribe(response =>{
      this.info = response;
    })
  }

  logout(){
    localStorage.clear();
    this.router.navigate(["/"]);
  }

}
