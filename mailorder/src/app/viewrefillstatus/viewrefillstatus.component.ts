import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RefillOrder } from 'src/model/RefillOrder';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-viewrefillstatus',
  templateUrl: './viewrefillstatus.component.html',
  styleUrls: ['./viewrefillstatus.component.css']
})
export class ViewrefillstatusComponent implements OnInit {

  constructor(private service:MailserviceService,private router:Router,private route:ActivatedRoute) {
   }
  
  refillOrder: RefillOrder = new RefillOrder();
  ngOnInit(): void {
    let sid:number = 0;
   this.route.params.subscribe(params => {
      sid = params['sid'];
      console.log(params) //log the entire params object
      console.log(params['sid']) //log the value of id
    });
    console.log(sid);
    this.service.isLogin().subscribe(data=>{
      if(!data){
        this.router.navigate(['/login']);
      }
    });
    this.getStatus(sid);
  }
 
  getStatus(id:number){
     this.service.getRefillStatus(id).subscribe((data :RefillOrder)=>{
         this.refillOrder = data;
        
         console.log(data + " " +this.refillOrder);
     },
     error=>{
       console.log(error);

       if(error.status==302){
        this.service.getRefillOrderList().subscribe(data=>{
          console.log(data);
          // if(data.length == 1){
          //   this.subscriptionList.push(data);
          // }

          this.refillOrder = data[data.length-1];   
    
        });
        // this.offers = []
        // this.pageError = "No offers found , to add a new offer click on Post an offer"
      }

     });
  }

}
