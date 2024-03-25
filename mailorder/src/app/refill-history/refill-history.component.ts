import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RefillOrder } from 'src/model/RefillOrder';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-refill-history',
  templateUrl: './refill-history.component.html',
  styleUrls: ['./refill-history.component.css']
})
export class RefillHistoryComponent implements OnInit {

  refillOrderList:RefillOrder[] = [];

  headElements = ['RefillOrder ID','Subcription ID', 'Date', 'Payment Status'];

  constructor(private service : MailserviceService, private router:Router) { }

  ngOnInit(): void {
    this.populateList();
    this.service.isLogin().subscribe(data=>{
      if(!data){
        this.router.navigate(['/']);
      }
    });

    
  }

  populateList(){
  
    this.service.getRefillOrderList().subscribe(data=>{
      console.log(data);
      // if(data.length == 1){
      //   this.subscriptionList.push(data);
      // }
      this.refillOrderList = data;


    });
  }
    

}
