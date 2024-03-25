import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { MailserviceService } from './mailservice.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  response: boolean = false;
  constructor(private routeService:MailserviceService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    this.response = this.routeService.canActive();
    console.log(this.response)
    if(!this.response){
      alert("You are not eligible to access this page without login")
        this.router.navigate(["/"])
    }
    return this.response;
  }
}
