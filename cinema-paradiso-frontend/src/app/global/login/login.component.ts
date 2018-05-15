import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import {User} from '../../user/user/user.model';
import {LoginService} from './login.service';
import {LoginStatusService} from './login.status.service';
import {ToastrService} from 'ngx-toastr';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginService]
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService, private loginStatusService: LoginStatusService, private toastr: ToastrService) {
  }

  user = new User();
  isSamePassword: boolean;
  email: string;
  password: string;
  emailTaken: boolean;
  userNameTaken: boolean;


  ngOnInit() {
    this.email = null;
    this.password = null;
    this.user = new User();
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
    if(this.user.email == null){
      this.toastr.error('Please enter email');
    }
    if(this.user.username == null){
      this.toastr.error('Please enter username');
    }
    if(this.user.password == null){
      this.toastr.error('please enter password');
    }
    if(!this.isSamePassword){
      this.toastr.error('You entered two different password');
    }
    if (!this.emailTaken ) {
      if (!this.userNameTaken && this.user !== undefined){
        if (this.user.email.includes("admin")){
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
                alert('One last step: Please activate your account by Email\nThe link will be Invalid in TEN minutes');
                location.reload(true);
              } else {
                alert('Invalid email address, please resignUp with an valid one');
                location.reload(true);
              }
            },
            error => {
              console.error('Error SignUp');
              this.toastr.error('Email address exist');
              return Observable.throw(error);
            });
        }
      }else{
        this.toastr.error('User name exist');
      }
    }else {
      this.toastr.error('Email address exist');
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
      if (this.email.includes("admin")){
        this.loginService.adminLogin(this.email, this.password).subscribe(data => {
          localStorage.setItem('credential', JSON.stringify(data));
          localStorage.setItem('token', JSON.stringify(data['token']));
          // Set user loggedIn status to global. So header can subscribe to the event.
          this.loginStatusService.changeStatus(true);
        });
      } else {
        this.loginService.login(this.email, this.password).subscribe(data => {
          if (data !== null) {
            localStorage.setItem('credential', JSON.stringify(data));
            localStorage.setItem('token', JSON.stringify(data['token']));
            // Set user loggedIn status to global. So header can subscribe to the event.
            this.loginStatusService.changeStatus(true);
            // location.reload(true);
          } else {
            alert('We detect your account is not activated\nWe resend you a verify email to your registration email, please check\n' +
              'The link will be Invalid in TEN minutes ');
          }
        },
          error => {
            console.error('Error Login');
            this.toastr.error('Wrong Email address or password');
            return Observable.throw(error);
          });
      }
    }else{
      this.toastr.error('Please enter your Email address and password');
    }
  }
  forgotPassword() {
    if (this.email !== null) {
      this.loginService.forgotPassword(this.email).subscribe(data => {
        if (data === true) {
          alert('A reset password email has been sent to your account email box, please check.\nThe link will be Invalid in TEN minutes');
          }
        });
      }else {
      this.toastr.error('Please enter your Email address');
    }
    }

}
