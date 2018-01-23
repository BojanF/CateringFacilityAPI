import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "../service/http/login/login.service";

@Component({
  selector: 'app-developer',
  templateUrl: './developer.component.html',
  styleUrls: ['./developer.component.css']
})
export class DeveloperComponent implements OnInit {

  constructor(private router:Router,private loginService:LoginService) { }

  ngOnInit() {
    if(localStorage.role!="developer"){
      this.router.navigateByUrl("/");
    }
  }

  logout(){
    this.loginService.logOut();
  }

}
