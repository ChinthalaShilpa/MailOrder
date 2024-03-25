import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtRequest } from 'src/model/JwtRequest';
import { MailserviceService } from '../mailservice.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  //User:JwtRequest = new JwtRequest('','');
  //to display errors
  formError = ""

  constructor(private service : MailserviceService,
    private router: Router) { }

    loginForm = new FormGroup({
     userName: new FormControl('',[
      Validators.required,
      Validators.minLength(3)
     ]),
     password: new FormControl('',[
      Validators.required
     ])
   });
  ngOnInit(): void {
  }

  get userName() { return this.loginForm.get('userName') }
  get password() { return this.loginForm.get('password') }

  authorize(){
    let request = new JwtRequest(this.loginForm.value.userName, this.loginForm.value.password);
    this.service.userlogin(request);
    let result = this.service.isLogin();
    result.subscribe(data =>{
      if(data){
        this.router.navigate(['dashboard'])
      }
    },
    error=>{
      if(error.status == 0 || error.status == 401){
        console.log("Credentials")
       this.formError = "Credentials are incorrect"
      }
      else{
        this.formError = "Something went wrong.Please try again after some time!!"
      }
    });
  }

}
