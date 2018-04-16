import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../global/login/login.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AdminService} from '../admin/admin.service';
import {RegUserService} from '../reg-user/reg-user.service';
import {HomeService} from '../../global/home/home.service';
import {LoginStatusService} from '../../global/login/login.status.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Movie} from '../../global/models/movie.model';
import {Celebrity} from '../../global/models/celebrity.model';
import {User} from '../user/user.model';
import {Review} from '../../global/models/review.model';
import {Application} from '../../global/models/application.model';

class Profile {
  name: string;
  id: number;
  username: string;
  email: string;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  providers: [AdminService, HomeService, RegUserService, LoginService]
})
export class AdminComponent implements OnInit {
  profile = new Profile();
  tokenHelper = new JwtHelperService();
  carousel: Movie[];
  allMovies: Movie[];
  allCelebrities: Celebrity[];
  allUsers: User[];
  allReviews: Review[];
  allApplications: Application[];
  currentAdIndex: number = -1;
  constructor(private router: Router, private loginService: LoginService, private modalService: NgbModal, private regUserService: RegUserService, private adminService: AdminService, private homeService: HomeService, private loginStatusService: LoginStatusService, private toastr: ToastrService) {
  }

  ngOnInit() {
    // load users
   // this.getUsers();
    // load movies
    this.getMovies();
    // load reviews
    this.getReviews();
    // load celebrities
    this.getCelebrities();
    // load carousel
    this.getCarousel();
    // load applications
    this.getApplications();

    // load admin info
    if (this.loginStatusService.getTokenDetails() !== null) {
      this.loginStatusService.changeStatus(true);
      this.regUserService.getProfile().subscribe(profileDetails => {
        console.log(profileDetails);

        this.profile = profileDetails as Profile;
        const decodedToken = this.tokenHelper.decodeToken(localStorage.getItem('token'));
        this.profile.email = decodedToken['email'];
        this.profile.id = decodedToken['profileId'];
        this.profile.username = decodedToken['username'];
      });
    }

    $(document).ready(function () {
      $('.mp-users').show();
      $('.mp-movies').hide();
      $('.mp-application').hide();
      $('.mp-carousel').hide();
      $('.mp-reviews').hide();
      $('.mp-celebrity').hide();

      $('body').on('click', '.larg div h3', function () {
        if ($(this).children('span').hasClass('close')) {
          $(this).children('span').removeClass('close');
        } else {
          $(this).children('span').addClass('close');
        }
        $(this).parent().children('p').slideToggle(250);
      });

      $('body').on('click', 'nav ul li a', function () {
        let title = $(this).data('title');
        $('.title').children('h2').html(title);

      });


      $('#manage-user').click(e => {
        $('.mp-users').show();
        $('.mp-movies').hide();
        $('.mp-application').hide();
        $('.mp-carousel').hide();
        $('.mp-reviews').hide();
        $('.mp-celebrity').hide();
      });

      $('#manage-movies').click(e => {

        $('.mp-users').hide();
        $('.mp-movies').show();
        $('.create_new_movie').hide();
        $('.movie_detail').hide();
        $('.movie_poster').hide();
        $('.mp-application').hide();
        $('.mp-carousel').hide();
        $('.mp-reviews').hide();
        $('.mp-celebrity').hide();
      });


      $('#manage-reviews').click(e => {

        $('.mp-users').hide();
        $('.mp-movies').hide();
        $('.mp-application').hide();
        $('.mp-carousel').hide();
        $('.mp-reviews').show();
        $('.mp-celebrity').hide();
      });

      $('#manage-celebrities').click(e => {

        $('.mp-users').hide();
        $('.mp-movies').hide();
        $('.mp-application').hide();
        $('.mp-carousel').hide();
        $('.mp-reviews').hide();
        $('.mp-celebrity').show();
      });


      $('#manage-carousel').click(e => {

        $('.mp-users').hide();
        $('.mp-movies').hide();
        $('.mp-application').hide();
        $('.mp-carousel').show();
        $('.mp-reviews').hide();
        $('.mp-celebrity').hide();
      });

      $('#manage-application').click(e => {

        $('.mp-users').hide();
        $('.mp-movies').hide();
        $('.mp-application').show();
        $('.mp-carousel').hide();
        $('.mp-reviews').hide();
        $('.mp-celebrity').hide();
      });

      $('.mp-movies__item').click(e => {
        $('.movie_detail').show();
      });
    });


  } // end init

  getCarousel():any {
    this.homeService.getCarousel()
      .subscribe(
        data => {
          // console.log(data);
          this.carousel = data as Movie[];
          console.log("carousels: ");
          console.log(this.carousel);
        },
        error => console.log('Failed to fetch carousel data')
      );
  }

  getMovies():any {
    this.adminService.getMovies()
      .subscribe(
        data => {
          // console.log(data);
          this.allMovies = data as Movie[];
          console.log("movies: ");
          console.log(this.allMovies);
        },
        error => console.log('Failed to fetch movie data')
      );
  }

  getCelebrities():any {
    this.adminService.getCelebrities()
      .subscribe(
        data => {
          // console.log(data);
          this.allCelebrities = data as Celebrity[];
          console.log("Celebrities: ");
          console.log(this.allCelebrities);
        },
        error => console.log('Failed to fetch celebrity data')
      );
  }

  getUsers():any {
    this.adminService.getUsers()
      .subscribe(
        data => {
          // console.log(data);
          this.allUsers = data as User[];
          console.log("users: ");
          console.log(this.allUsers);
        },
        error => console.log('Failed to fetch user data')
      );
  }

  getReviews():any {
    this.adminService.getReviews()
      .subscribe(
        data => {
          // console.log(data);
          this.allReviews = data as Review[];
          console.log("reviews: ");
          console.log(this.allReviews);
        },
        error => console.log('Failed to fetch review data')
      );
  }

  getApplications():any {
    this.adminService.getApplications()
      .subscribe(
        data => {
          // console.log(data);
          this.allApplications = data as Application[];
          console.log("applications: ");
          console.log(this.allApplications);
        },
        error => console.log('Failed to fetch application data')
      );
  }


  showAddNewMovieForm(){
    if ($(".movie_poster").is(":visible")){
      $(".movie_poster").hide();
    }
    if ($(".movie_detail").is(":visible")){
      $(".movie_detail").hide();
    }
    if ($(".create_new_movie").is(":visible")){
      $(".create_new_movie").hide();
    }
    else{
      $(".create_new_movie").show();
    }

  }
  showMovieDetail(m, i){
    if ($(".create_new_movie").is(":visible")){
      $(".create_new_movie").hide();
    }
    if ($(".movie_poster").eq(i).is(":visible")){
      $(".movie_poster").eq(i).hide();
    }
    if ($(".movie_detail").eq(i).is(":visible")){
      $(".movie_detail").eq(i).hide();
    }
    else{
      $(".movie_detail").eq(i).show();
    }
  }

  showPoster(m, i){
    if ($(".create_new_movie").is(":visible")){
      $(".create_new_movie").hide();
    }
    if ($(".movie_detail").eq(i).is(":visible")){
      $(".movie_detail").eq(i).hide();
    }
    if ($(".movie_poster").eq(i).is(":visible")){
      $(".movie_poster").eq(i).hide();
    }
    else{
      $(".movie_poster").eq(i).show();
    }
  }

  deleteMovie(m){
    var result = confirm("Want to delete movie "+ m.title);
    if(result){
      console.log("deleting: "+m.imdbId);
      this.adminService.deleteMovie(m.imdbId);
      console.log("deleted")
    }
  }

  cancel_add_movie(){
    if ($(".movie_poster").is(":visible")){
      $(".movie_poster").hide();
    }
    if ($(".movie_detail").is(":visible")){
      $(".movie_detail").hide();
    }
    $(".create_new_movie").hide();
  }
}//end oninit
