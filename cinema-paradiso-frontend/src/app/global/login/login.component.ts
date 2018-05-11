import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import {User} from '../../user/user/user.model';
import {LoginService} from './login.service';
import {LoginStatusService} from './login.status.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginService]
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService, private loginStatusService: LoginStatusService) {
  }

  user = new User();
  isSamePassword: boolean;
  email: string;
  password: string;
  emailTaken: boolean;
  userNameTaken: boolean;


  ngOnInit() {
    const signupButton = document.getElementById('signup-button'),
      loginButton = document.getElementById('login-button'),
      userForms = document.getElementById('user_options-forms');

    /**
     * Add event listener to the "Sign Up" button
     */
    signupButton.addEventListener('click', () => {
      userForms.classList.remove('bounceRight');
      userForms.classList.add('bounceLeft');
    }, false);

    /**
     * Add event listener to the "Login" button
     */
    loginButton.addEventListener('click', () => {
      userForms.classList.remove('bounceLeft');
      userForms.classList.add('bounceRight');
    }, false);
  }


  signup() {
    // check password is the same
    if (!this.emailTaken && !this.userNameTaken && this.user !== undefined) {
      if(this.user.email.includes("admin")){
        this.loginService.adminSingup(this.user).subscribe(data => {
          localStorage.setItem('credential', JSON.stringify(data));
          localStorage.setItem('token', JSON.stringify(data['token']));

          // Set user loggedIn status to global. So header can subscribe to the event.
          this.loginStatusService.changeStatus(true);
        });
      } else {
        this.loginService.singup(this.user).subscribe(data => {
          // localStorage.setItem('credential', JSON.stringify(data));
          // localStorage.setItem('token', JSON.stringify(data['token']));

          // Set user loggedIn status to global. So header can subscribe to the event.
          // this.loginStatusService.changeStatus(true);
          if(data !== null){
            alert('One last step: Please activate your account by Email\n The link will be Invalid in TEN minutes');
            location.reload(true);
          } else {
            alert('Invalid email address, please resignUp with an valid one');
            location.reload(true);
          }
        });
      }
    }
  }

  checkSamePassword(password: string, event) {
    if (password === event.target.value) {
      this.isSamePassword = true;
    } else {
      this.isSamePassword = false;
    }
  }

  checkEmailTaken(email: string) {
    this.loginService.checkEmailTaken(email).then(result => {
      if (result['taken'] === true) {
        console.log('email taken');
        this.emailTaken = true;
      } else {
        this.emailTaken = false;
      }
    });
  }

  checkUserName(username: string) {
    this.loginService.checkUserName(username).then(result => {
        if (result['taken'] === true) {
          console.log('username taken');
          this.userNameTaken = true;
        } else {
          this.userNameTaken = false;
        }
      }
    );
  }

  login() {
    if (this.email !== null && this.password !== null) {
      if(this.email.includes("admin")){
        this.loginService.adminLogin(this.email, this.password).subscribe(data => {
          localStorage.setItem('credential', JSON.stringify(data));
          localStorage.setItem('token', JSON.stringify(data['token']));
          // Set user loggedIn status to global. So header can subscribe to the event.
          this.loginStatusService.changeStatus(true);
        });
      }
      else{
        this.loginService.login(this.email, this.password).subscribe(data => {
          if (data !== null) {
            localStorage.setItem('credential', JSON.stringify(data));
            localStorage.setItem('token', JSON.stringify(data['token']));
            // Set user loggedIn status to global. So header can subscribe to the event.
            this.loginStatusService.changeStatus(true);
            location.reload(true);
          } else {
            alert(' Please activate your account by Email');
          }
        });
      }
    }
  }
  forgotPassword() {
    if (this.email !== null) {
      this.loginService.forgotPassword(this.email).subscribe(data => {
        if (data === true) {
          alert('A reset password email has been sent to your account email box, please check.\nThe link will be Invalid in TEN minutes');
        }
      });
    } else {
      alert('Please enter your account email address!');
    }
  }



}
