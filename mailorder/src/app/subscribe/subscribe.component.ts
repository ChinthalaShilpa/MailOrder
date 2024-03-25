import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MemberPrescription } from 'src/model/MemberPrescription';
import { Status } from 'src/model/Status';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {

  date=new Date(Date.now()).toLocaleDateString();

  dname:string = "";

  dloc :string = "";
  
  // 
  prescriptionForm = new FormGroup({

    memberLocation: new FormControl(''),
    quantity: new FormControl(''),
    drugName : new FormControl(''),
    doctorName: new FormControl(''),
    course: new FormControl(''),
    
  });
  locationList : string[] = ["Mumbai","Chennai","Bangalore","Pune","Kolkata","Hyderabad","Delhi"];
  courseList : string[] = ["weekly","monthly"]
  status!:Status;
  // latest_date:string = ""+this.datepipe.transform(this.date, 'yyyy-MM-dd');
  constructor(private service : MailserviceService, private router:Router, private route:ActivatedRoute) { 
    
  }



  ngOnInit(): void {
    this.service.isLogin().subscribe(data=>{
      if(!data){
        this.router.navigate(['/']);
      }
    });

    this.route.params.subscribe(params => {

      this.dname = params['dname'];

      this.dloc = params['dloc'];

      console.log(params) //log the entire params object

      console.log(params['sid']) //log the value of id

    });


   
  }
  onSubmit(){
    console.log(this.prescriptionForm.value.drugName);
    console.log(this.service.getUserName());
    this.service.isLogin().subscribe(data=>{
      if(!data){
        this.router.navigate(['/login']);
      }
    });
  
    
     let prescription = new MemberPrescription(
       this.service.getUserName(),
       this.prescriptionForm.value.memberLocation,
       this.formatDate(),
       this.prescriptionForm.value.quantity,
       this.prescriptionForm.value.drugName,
       this.prescriptionForm.value.doctorName,
       this.prescriptionForm.value.course
      );
      console.log(this.formatDate());
     console.log(prescription);
      this.service.addSubscription(prescription).subscribe(data=>{
         this.status = data;
         console.log(this.status);
      });
      console.log(this.status);

  }
formatDate() {
    var d = new Date(Date.now()),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [day,month,year].join('/');
}
 

 
}
