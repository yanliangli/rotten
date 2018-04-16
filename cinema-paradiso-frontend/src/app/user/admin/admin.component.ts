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
    // this.getMovies();
    // // load reviews
    // this.getReviews();
    // // load celebrities
    // this.getCelebrities();
    // // load carousel
    // this.getCarousel();
    // // load applications
    // this.getApplications();

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
    this.cancelAddMovie();
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
    if ($(".movie_detail").eq(i).is(":visible")){$(".movie_detail").eq(i).hide();}
    else{$(".movie_detail").eq(i).show();}

    if ($(".create_new_movie").is(":visible")){$(".create_new_movie").hide();}
    if ($(".movie_poster").eq(i).is(":visible")){$(".movie_poster").eq(i).hide();}

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

  addMovie(){
    // var data = $('#add_movie_form').serializeArray().reduce(function(obj, item) {
    //   obj[item.name] = item.value;
    //   return obj;
    // }, {});
    // console.log("data: ");
    // console.log(data);
    // this.adminService.createMovie(data).subscribe(
    //   data => {
    //     // refresh the list
    //     this.getMovies();
    //     return true;
    //   },
    //   error => {
    //     console.error("Error creating movie!");
    //     return Observable.throw(error);
    //   }
    // );
    let movie={
      "imdbId": "tt999991499494999999999",
        "title": "I Love CS",
        "year": 2017,
        "rated": "R",
        "releaseDate": 20171121,
        "genres": ["CRIME", "DRAMA"],
        "awards": ["Nominated for 3 BAFTA Film Awards"],
        "photos": ["https://images-na.ssl-images-amazon.com/images/M/MV5BNTA3ODgwNjc3NV5BMl5BanBnXkFtZTgwMTIyNTYxNDM@.jpg"],
        "director": {
        "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm0015359",
          "name": "Fatih Akin",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": true,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
      },
      "trailers": [],
        "reviews": [],
        "plot": "Katja's life collapses after the death of her husband and son in a bomb attack. After a time of mourning and injustice, Katja seeks revenge.",
        "language": "English",
        "country": "USA",
        "poster": "https://images-na.ssl-images-amazon.com/images/M/MV5BMTE0MDQxODg2NTJeQTJeQWpwZ15BbWU4MDIwODkyMjQz._V1_SX300.jpg",
        "rating": 4.7,
        "production": "Alcon Entertainment",
        "website": "",
        "boxOffice": 191925612,
        "runTime": 105,
        "casts": [
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm5645519",
          "name": "Anthony Gonzalez",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": false,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        },
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm0305558",
          "name": "Gael García Bernal",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": false,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        },
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm0000973",
          "name": "Benjamin Bratt",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": false,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        }
      ]
    };
    this.adminService.createMovie(movie).subscribe(
      data => {
        // refresh the list
        this.getMovies();
        return true;
      },
      error => {
        console.error("Error creating movie!");
        return Observable.throw(error);
      }
    );
  }

  updateMovie(){
    let m = {
      "imdbId": "tt5580390",
      "title": "The Shape of Computer Science",
      "year": 2017,
      "rated": "R",
      "releaseDate": 20171118,
      "genres": ["ADVENTURE", "DRAMA", "FANTASY", "HORROR", "ROMANCE"],
      "awards": ["academy awarded"],
      "photos": ["../../../assets/images/carousel_img/2.jpg_)"],
      "director": {
        "PHOTO_LOCATION": "/tmp/celebrity",
        "celebrityId": "nm0868219",
        "name": "Guillermo del Toro",
        "profileImage": null,
        "biography": "Guillermo del Toro was born October 9, 1964 in Guadalajara Jalisco, Mexico. Raised by his Catholic grandmother, del Toro developed an interest in filmmaking in his early teens...",
        "birthDate": null,
        "birthCity": null,
        "birthState": null,
        "birthCountry": null,
        "filmography": [],
        "director": true,
        "photo_LOCATION": "/tmp/celebrity",
        "profileImageName": null
      },
      "trailers": [],
      "reviews": [],
      "plot": "At a top secret research facility in the 1960s, a lonely janitor forms a unique relationship with an amphibious creature that is being held in captivity.",
      "language": "English",
      "country": "USA",
      "poster": null,
      "rating": 4.3,
      "production": "A24 Films",
      "website": "http://moonlight.movie/",
      "boxOffice": 19400000,
      "runTime": 111,
      "cast": [
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm1020089",
          "name": "Sally Hawkins",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": true,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        },
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm0788335",
          "name": "Michael Shannon",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": true,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        },
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm0420955",
          "name": "Richard Jenkins",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": true,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        },
        {
          "PHOTO_LOCATION": "/tmp/celebrity",
          "celebrityId": "nm0818055",
          "name": "Octavia Spencer",
          "profileImage": null,
          "biography": null,
          "birthDate": null,
          "birthCity": null,
          "birthState": null,
          "birthCountry": null,
          "filmography": [],
          "director": true,
          "photo_LOCATION": "/tmp/celebrity",
          "profileImageName": null
        }
      ]
    }
    this.adminService.updateMovie(m).subscribe(
      data => {
        // refresh the list
        this.getMovies();
        return true;
      },
      error => {
        console.error("Error updating "+m.title);
        return Observable.throw(error);
      }
    );
  }

  cancelAddMovie(){
    $(".movie_poster").hide();
    $(".movie_detail").hide();
    $(".create_new_movie").hide();
  }

  deleteMovie(m){
    var result = confirm("Want to delete movie "+ m.title);
    if(result){
      console.log("deleting: "+m.imdbId);
      this.adminService.deleteMovie(m.imdbId);
      console.log("deleted")
    }
  }


}//end oninit
