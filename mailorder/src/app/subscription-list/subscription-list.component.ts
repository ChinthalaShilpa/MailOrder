import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MemberSubscription } from 'src/model/MemberSubscription';
import { RefillOrder } from 'src/model/RefillOrder';
import { RefillPojo } from 'src/model/RefillPojo';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-subscription-list',
  templateUrl: './subscription-list.component.html',
  styleUrls: ['./subscription-list.component.css']
})
export class SubscriptionListComponent implements OnInit {

  constructor(private service:MailserviceService, private router:Router) { }
  sid:number = 0;
  refillOrderList:RefillOrder[] = [];
  subscriptionList:MemberSubscription[] = []

checkform = new FormGroup({
  check: new FormControl(''),
});

  headElements = ['Subcription ID', 'Member Location', 'Drug Name','Quantity', 'LastDate', 'Refilling Frequency', 'View Status','Add Order','UnSubscribe'];

  refillOrder!:RefillOrder;

  ngOnInit(): void {

    this.service.isLogin().subscribe(data=>{

      if(!data){

        this.router.navigate(['/']);

      }

    });

    this.populateList();

     this.populateList2(this.sid);
  

  }



  populateList(){

    this.service.getSubscriptionList().subscribe(data=>{
      console.log(data);
      // if(data.length == 1){
      //   this.subscriptionList.push(data);
      // }
      this.subscriptionList = data;


    });

  }

  

  viewStatus(id : number){

      this.router.navigateByUrl('/viewrefillstatus/'+id);

  }

  orderRefill(id:number){
    console.log(this.checkform.value.check);
    console.log(id);
    
    let refill =new RefillPojo(this.checkform.value.check,this.service.getUserName(),id);
    this.service.addRefillOrder(refill).subscribe();
    window.location.reload();

  }

  unsubscribe(id:number){
    this.sid = id;
    let i=this.populateList2(id);
    console.log(i);
    if(i==0){
      console.log(id)
      this.service.unSubscribe(id).subscribe(data=>{
        console.log(data)   
      });
    }
   
    window.location.reload();
  }

  populateList2(id:number){
  
    this.service.getRefillOrderListWithDues().subscribe(data=>{
     // console.log(data);
      // if(data.length == 1){
      //   this.subscriptionList.push(data);
      // }
      
      this.refillOrderList = data;

      //console.log(this.refillOrderList)
     
      // if(this.refillOrderList.length==0){
        
      // }
    });
    let count1=0;
    for(let count=0;count<this.refillOrderList.length;count++){
      if(this.refillOrderList[count].subsId==id){
        count1+=1;
      }

    }
    console.log(count1)
    return count1;
  }

}
