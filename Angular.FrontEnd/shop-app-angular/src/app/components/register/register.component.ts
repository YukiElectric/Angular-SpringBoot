import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm !: NgForm;
  phone : string = '';
  password : string = '';
  retypePassword : string = '';
  name : string = '';
  address : string = '';
  isAccepted : boolean = false;
  dateOfBirth : Date = new Date();
  register() {
    alert('Register');
  }
}
