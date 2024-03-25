import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private service : MailserviceService, private router:Router) { }
  islogin!: boolean;
  ngOnInit(): void {
    this.service.isLogin().subscribe(data=>{
      this.islogin =data;
      if(!data){
         this.router.navigate(['/']);
      }
    });
  }

  userid = this.service.getUserName();
}
