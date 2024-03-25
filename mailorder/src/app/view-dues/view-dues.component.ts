import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DuesInformation } from 'src/model/DuesInformation';
import { RefillOrder } from 'src/model/RefillOrder';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-view-dues',
  templateUrl: './view-dues.component.html',
  styleUrls: ['./view-dues.component.css']
})
export class ViewDuesComponent implements OnInit {
  refillOrderList:RefillOrder[] = [];
  dueList :DuesInformation[] = [];
  constructor(private service:MailserviceService, private router:Router) {
    //this.populateList();
   }
  headElements = ['Subcription ID', 'Drug Name','Days Not Filled'];

  headElements1 = ['RefillOrder ID','Subcription ID', 'Date', 'Payment Status'];
  ngOnInit(): void {
    //this.populateList();
    this.service.isLogin().subscribe(data=>{
      if(!data){
        this.router.navigate(['login']);
      }
    });
    this.populateDueList();
    this.populateList();
  }

 
  populateDueList(){

    this.service.getDuesList().subscribe(data=>{
      console.log(data);
    //    if(data.length == 1){
    //      this.dueList.push(data);
    //  }
      this.dueList = data;
      console.log(this.dueList);


    });
    //this.populateDueList();

  }

  populateList(){
  
    this.service.getRefillOrderListWithDues().subscribe(data=>{
      console.log(data);
      // if(data.length == 1){
      //   this.subscriptionList.push(data);
      // }
      this.refillOrderList = data;


    });
  }
    





}
