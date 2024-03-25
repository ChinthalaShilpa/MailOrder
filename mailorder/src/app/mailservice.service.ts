import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DrugDetail } from 'src/model/DrugDetail';
import { DuesInformation } from 'src/model/DuesInformation';
import { JwtRequest } from 'src/model/JwtRequest';
import { JwtResponse } from 'src/model/JwtResponse';
import { MemberPrescription } from 'src/model/MemberPrescription';
import { MemberSubscription } from 'src/model/MemberSubscription';
import { RefillOrder } from 'src/model/RefillOrder';
import { RefillPojo } from 'src/model/RefillPojo';
import { Status } from 'src/model/Status';

@Injectable({
  providedIn: 'root'
})
export class MailserviceService {
  getRefillOrderListWithDues() {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<RefillOrder[]>("http://127.0.0.1:8300/refill/getrefillpaymentdues/"+this.getUserName(),{headers});
  }

  constructor(private http:HttpClient) { }
  unSubscribe(sid:number) {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<Status>("http://127.0.0.1:8200/subscription/unsubscribe/"+sid,{headers});
  }

  canActive(): boolean {
    let tokens: any = sessionStorage.getItem("Authorization");
    if(tokens != null){

      return true;
    }
    return false;
  }
  getDuesList() {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<DuesInformation[]>("http://127.0.0.1:8300/refill/getthesubcriptionidshastorefill/"+this.getUserName(),{headers});
    
  }
  getRefillOrderList() {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<RefillOrder[]>("http://127.0.0.1:8300/refill/getrefillorders/"+this.getUserName(), {headers});
  }
  addRefillOrder(refill: RefillPojo) {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.post<RefillOrder>("http://127.0.0.1:8300/refill/requestadhocrefill",refill,{headers});
  }
  
  searchByLocation(searchDrugLoc: any,searchDrug:any) {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<DrugDetail>("http://127.0.0.1:8100/drug/searchbylocationanddrugid/"+searchDrug+"/"+searchDrugLoc,{headers});
  
  }

  getRefillStatus(id:number) {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<RefillOrder>("http://127.0.0.1:8300/refill/viewrefillstatus/"+id,{headers});
 
  }
  addSubscription(prescription: MemberPrescription) :Observable<Status>{
    
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.post<Status>("http://127.0.0.1:8200/subscription/subscribe", prescription,{headers})
 
  }

  
  getUserName():any{
    return sessionStorage.getItem("userName");
  }

  userlogin(request: JwtRequest){
    let response = this.http.post<JwtResponse>("http://127.0.0.1:8400/auth/authenticate",request);
    response.subscribe(data =>{
      sessionStorage.setItem("userName", request.userName);
      sessionStorage.setItem("Authorization", data.token);
    });
  }

  isLogin(): Observable<boolean>{
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    let response = this.http.post<boolean>("http://127.0.0.1:8400/auth/authorize",Boolean, { headers });
    return response;
  }

  searchByName(searchDrug: any) {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<DrugDetail>("http://127.0.0.1:8100/drug/searchdrugbyname/"+searchDrug,{headers});
  
  }
  searchById(searchDrug: any) {
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<DrugDetail>("http://127.0.0.1:8100/drug/searchdrugbyid/"+searchDrug,{headers});
  
  }

  getDrugList(){
    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")
    const headers = new HttpHeaders().set('Authorization', tokens)
    return this.http.get<DrugDetail[]>("http://127.0.0.1:8100/drug/viewdrugs",{headers});
  }

  getSubscriptionList() {

    let tokens: string = 'Bearer ' + sessionStorage.getItem("Authorization")

    const headers = new HttpHeaders().set('Authorization', tokens)

    return this.http.get<MemberSubscription[]>("http://127.0.0.1:8200/subscription/viewsubscription/"+this.getUserName(),{headers});



  }
}
