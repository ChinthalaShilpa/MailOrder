import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Drug } from 'src/model/Drug';
import { DrugDetail } from 'src/model/DrugDetail';
import { DrugLocation } from 'src/model/DrugLocation';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-view-drugs',
  templateUrl: './view-drugs.component.html',
  styleUrls: ['./view-drugs.component.css']
})
export class ViewDrugsComponent implements OnInit {

  searchContent = new FormGroup({
    searchDrug: new FormControl(''),
    searchdrugbylocation:new FormControl('')
  });
  drugList : DrugDetail[] = [];
  drugLocationList : DrugLocation[] = [];
  drugs:Drug[]= [];
  orders : string[] = ["id", "name"];
  //headElements = ['Drug ID', 'Drug Name', 'Company', 'MF date', 'ExpiryDate','Quantity', 'Location'];
 
  headElements:string[]  = [];

  locationList : string[] = ["Select your Location","Mumbai","Chennai","Bangalore","Pune","Kolkata","Hyderabad","Delhi"];

  constructor(private service:MailserviceService, private router:Router) {
    this.headElements = ['Drug ID', 'Drug Name', 'Company', 'MF date', 'ExpiryDate','Quantity', 'Location','Subscribe'];
    // this.service.getDrugList().subscribe(data=>{
    //   console.log(data);
    //  this.drugList = data;
    // });
   }

  drugDetail!: DrugDetail;

  ngOnInit(): void {
    this.service.isLogin().subscribe(data=>{
      if(!data){
        this.router.navigate(['/']);
      }
    });

   this.service.getDrugList().subscribe(data=>{
     console.log(data);
    this.drugList = data;
    this.drugs = this.gettotalvalues(this.drugList);
     
   });
  }

  onSearch(){
    console.log(this.searchContent.value.searchDrug + " " + this.searchContent.value.searchdrugbylocation);
    if(this.searchContent.value.searchdrugbylocation != "" && this.searchContent.value.searchDrug !=""){
      this.service.searchByLocation(this.searchContent.value.searchdrugbylocation,this.searchContent.value.searchDrug).subscribe(data=>{
      this.drugList = [];
      this.drugs = [];
      this.drugList.push(data);
      this.drugs = this.gettotalvalues(this.drugList);
      console.log(data);
    });
  }
    else if(this.searchContent.value.searchDrug !="" && this.searchContent.value.searchdrugbylocation == ""){
      if(/\d/.test(this.searchContent.value.searchDrug)){
        this.service.searchById(this.searchContent.value.searchDrug).subscribe(data=>{
          this.drugList = [];
          this.drugs = [];
          this.drugList.push(data);
          this.drugs = this.gettotalvalues(this.drugList);
          console.log(data);
      });
      }
      else{
        this.service.searchByName(this.searchContent.value.searchDrug).subscribe(data=>{
          this.drugList = [];
          this.drugs = [];
          this.drugList.push(data);
          this.drugs = this.gettotalvalues(this.drugList);
          //this.drugDetail = data;
          console.log(data);
      });
    }
      
  }
  else{
    alert("you have to enter drug id or name (or) enter drug id to search with your location");
  }

    console.log(this.drugDetail);
  }


  gettotalvalues(drugList:DrugDetail[]){
    for(let i=0;i<drugList.length;i++){
      for(let j=0;j<drugList[i].drugLocationList.length;j++){
        this.drugs.push(new Drug(drugList[i].drugId,drugList[i].drugName,drugList[i].manufacturerName,drugList[i].manufacturedDate,drugList[i].expiryDate,drugList[i].drugLocationList[j].quantity,drugList[i].drugLocationList[j].location))
      }
    }
    console.log(this.drugs);
    return this.drugs;

  }

  getSubscribe(drugName:string, drugLocation:string){

    this.router.navigateByUrl("subscribe/"+drugName+"/"+drugLocation);

  }
}
