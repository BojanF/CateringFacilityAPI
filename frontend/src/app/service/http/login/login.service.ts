import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RequestOptions} from "@angular/http";
import { HttpHeaders } from '@angular/common/http'
import {IdService} from "../../id-service/id.service";
import {Router} from "@angular/router";

@Injectable()
export class LoginService {

  private authenticated:boolean;
  constructor(private http: HttpClient,private idService:IdService,private router:Router) {

  }

  authenticate(credentials, callback) {

    var headers = credentials ? {
      authorization : "Basic " + btoa(credentials.username + ":" + credentials.password),
      "Content-Type": 'application/x-www-form-urlencoded'
    } : {};
    this.http.get('http://localhost:8080/user', {headers: new HttpHeaders(headers)}).subscribe(function(response) {
      if (response) {
        this.authenticated = true;
        var role = response["authorities"][0].authority.toLowerCase();
      } else {
        this.authenticated = false;
      }

      localStorage.base=headers["authorization"];
      localStorage.role=role;
      localStorage.id = response["principal"].id;
      callback(role,true);
    },function(){
      callback("",false);
    });

  }

  getCredentials(){
    var headers = {};

        headers["Authorization"] = localStorage.getItem("base");

   // headers[ "Content-Type"]= 'application/x-www-form-urlencoded';
    return new HttpHeaders(headers)};



  logOut(){
    localStorage.clear();
    this.http.post("http://localhost:8080/logout","",{headers:this.getCredentials()});
    this.router.navigateByUrl("/");
  }
}

