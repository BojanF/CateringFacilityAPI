import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../service/http/login/login.service";

@Component({
  selector: 'app-facility',
  templateUrl: './facility.component.html',
  styleUrls: ['./facility.component.css']
})
export class FacilityComponent implements OnInit {

  constructor(private router:Router, private loginService:LoginService) { }

  ngOnInit() {
    if(localStorage.role!="facility"){
      this.router.navigateByUrl("/");
    }
  }

  logout(){
    this.loginService.logOut();
  }

}
