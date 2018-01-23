import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {LoginService} from "../service/http/login/login.service";

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {

  constructor(private router:Router,private loginService:LoginService) { }

  ngOnInit() {
    if(localStorage.role!="admin"){
      this.router.navigateByUrl("/");
    }
  }

  logout(){
    this.loginService.logOut();
  }


}
