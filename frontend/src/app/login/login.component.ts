import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RequestOptions} from "@angular/http";
import {LoginService} from  "../service/http/login/login.service";
import { Router } from '@angular/router';
import {IdService} from "../service/id-service/id.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private title :string = 'app';
  private credentials={};
  private authenticated:boolean;


  constructor( private loginService: LoginService,
              private router:Router,
               private idService:IdService
              ) {
    this.authenticated=true;
  }

  ngOnInit() {
    if(localStorage.getItem("role")){
      this.router.navigateByUrl('/'+localStorage.role);
    }
  }


  login(){
    var self=this;
    this.loginService.authenticate(this.credentials, function(role,auth) {
      if(auth)
      {
        self.router.navigateByUrl('/'+role)
      }
        self.authenticated=auth;
      console.log(auth);
    });

    return false;
  }
}
