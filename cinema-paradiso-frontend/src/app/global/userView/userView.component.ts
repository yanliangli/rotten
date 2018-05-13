import {Component, OnInit} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {UserViewService} from './userView.service';
import {Token} from '../../global/login/token.model';
import {LoginStatusService} from '../../global/login/login.status.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {ToastrService} from 'ngx-toastr';
import {NgForm} from '@angular/forms';
import {LoginService} from '../../global/login/login.service';

import {ActivatedRoute, Router} from '@angular/router';
import {Movie} from '../../global/models/movie.model';
import {MovieService} from '../../global/movie/movie.service';
import {Review} from '../../global/models/review.model';
import {Observable} from 'rxjs/Observable';
import {UserFile} from '../models/userFile.model';

class Profile {
  name: string;
  profileId: number;
  profileImage: string;
  biography: string;
  isCritic: boolean;
  username: string;
  email: string;
}

@Component({
  selector: 'app-user-view',
  templateUrl: './userView.component.html',
  styleUrls: ['./userView.component.scss'],
  providers: [UserViewService, LoginService]
})
export class UserViewComponent implements OnInit {
  status: boolean;
  currentIndex = 1;
  closeReason: string;
  profile = new Profile();
  tokenHelper = new JwtHelperService();
  profile_url: string;
  whishlist: Movie[];
  watchlist: Movie[];
  ratedMovieList: Movie[];
  reviews: Review[];
  followerProfiles: UserFile[];
  followYouProfiles: UserFile[];
  userId: number;
  inFollower: boolean;
  constructor(private router: Router,
              private movieService: MovieService,
              private loginService: LoginService,
              private modalService: NgbModal,
              private userViewService: UserViewService,
              private loginStatusService: LoginStatusService,
              private toastr: ToastrService,
              route: ActivatedRoute) {
    this.userId = route.snapshot.params['id'];
  }

  showDiv(index) {
    this.currentIndex = index;
    console.log(this.currentIndex);
  }

  ngOnInit() {
    this.loadPosters();
    if (this.loginStatusService.getTokenDetails() !== null){
      this.loginStatusService.changeStatus(true);
      this.status = true;
      this.checkInFollower();
    }
    if (true) {
      this.getWishlist();
      this.getWatchlist();
      this.getRatedMovieList();
      this.getReviews();
      this.getFollowers();
      this.getFollowYou();
      this.userViewService.getProfile(this.userId).subscribe(profileDetails => {
        console.log(profileDetails);
        this.profile = profileDetails as Profile;
        // const decodedToken = this.tokenHelper.decodeToken(localStorage.getItem('token'));
        this.profile.email = profileDetails['email'];
        this.profile.profileId = profileDetails['profileId'];
        this.profile.username = profileDetails['username'];
        this.profile.profileImage = profileDetails['profileImage'];
        if (this.profile.profileImage === undefined) {
          this.profile_url = 'http://localhost:8080/user/avatar/default.jpeg';
        } else {
          this.profile_url = 'http://localhost:8080/user/avatar/' + profileDetails['profileImage'];
        }
      });
    }
  }
  setImdbId(imdbId: string) {
    this.movieService.setSelectedMovieId(imdbId);
  }
  followUser() {
    this.userViewService.followUser(this.userId).subscribe(result => {
      console.log(result);
      this.checkInFollower();
    });
  }
  deFollowUser() {
    this.userViewService.deFollowUser(this.userId).subscribe(result => {
      console.log(result);
      this.checkInFollower();
    });
  }
  checkInFollower(){
    this.userViewService.checkInFollower(this.userId).then(result => {
      if (result === true) {
        this.inFollower = true;
        console.log(this.inFollower);
      } else {
        this.inFollower = false;
        console.log(this.inFollower);
      }
    });
  }
  notice() {
    alert('Please Log in/Sign up first!');
  }
  getReviews(): any {
    this.userViewService.getReviews(this.userId)
      .subscribe(
        data => {
          this.reviews = data as Review[];
          console.log(this.reviews);
        },
        error => console.log('Failed to fetch movies playing')
      );
  }
  getRatedMovieList(): any {
    this.userViewService.getRatedMovieList(this.userId)
      .subscribe(
        data => {
          this.ratedMovieList = data as Movie[];
          console.log(this.ratedMovieList);
        },
        error => console.log('Failed to fetch movies playing')
      );
  }
  getWishlist(): any {
    this.userViewService.getWishlist(this.userId)
      .subscribe(
        data => {
          this.whishlist = data as Movie[];
          console.log(this.whishlist);
        },
        error => console.log('Failed to fetch movies playing')
      );
  }
  getWatchlist(): any {
    this.userViewService.getWatchlist(this.userId)
      .subscribe(
        data => {
          this.watchlist = data as Movie[];
          console.log(this.watchlist);
        },
        error => console.log('Failed to fetch movies playing')
      );
  }

  open(content) {
    this.modalService.open(content).result.then(result => {
      this.closeReason = `Reason ${result}`;
    }, (reason) => {
      this.closeReason = `Dismissed ${this.getDissmissReason(reason)}`;
    });
  }

  getDissmissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing escape';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking x';
    } else {
      return `With ${reason}`;
    }
  }
  getFollowers(): any {
    this.userViewService.getFollowers(this.userId)
      .subscribe(
        data => {
          this.followerProfiles = data as UserFile[];
          console.log(this.followerProfiles);
        },
        error => console.log('Failed to fetch movies playing')
      );
  }
  getFollowYou(): any {
    this.userViewService.getFollowYou(this.userId)
      .subscribe(
        data => {
          this.followYouProfiles = data as UserFile[];
          console.log(this.followYouProfiles);
        },
        error => console.log('Failed to fetch movies playing')
      );
  }


  loadPosters(): void {
    const movieNames = ['Blade Runner 2049', 'Coco', 'Call Me By Your Name', 'Lady Bird', 'Get Out', 'Dunkirk', 'In the Fade', 'Phantom Thread'];

    const images = ['https://images-na.ssl-images-amazon.com/images/M/MV5BNzA1Njg4NzYxOV5BMl5BanBnXkFtZTgwODk5NjU3MzI@._V1_SX300.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BYjQ5NjM0Y2YtNjZkNC00ZDhkLWJjMWItN2QyNzFkMDE3ZjAxXkEyXkFqcGdeQXVyODIxMzk5NjA@._V1_SX300.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BNDk3NTEwNjc0MV5BMl5BanBnXkFtZTgwNzYxNTMwMzI@._V1_SX300.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMjEzNzE1NF5BMl5BanBnXkFtZTgwNDYwNjUzMTI@.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BNjA4MzEzOTc0N15BMl5BanBnXkFtZTgwOTcyNDY4MjI@.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BMTYwNDI5Njg2M15BMl5BanBnXkFtZTgwMzIyNTYxNDM@.jpg',
      'https://images-na.ssl-images-amazon.com/images/M/MV5BOTE5MzkwMjM0NV5BMl5BanBnXkFtZTgwMTQ0Mjk0NDM@.jpg',
    ];

    const imageContainers: NodeListOf<Element> = document.getElementsByClassName('l-cards__image');
    const movieTitles: NodeListOf<Element> = document.getElementsByClassName('l-cards__text');

    let i = 0, y = 0;
    while (i < movieTitles.length) {
      // create img element and append to container
      const img = document.createElement('img');
      img.setAttribute('src', images[y]);
      img.setAttribute('alt', movieNames[y]);
      img.style.height = '16em';
      imageContainers[i].appendChild(img);

      // create span and append movie names and ratings
      const ratings = document.createElement('p');
      ratings.style.color = 'rgb(229, 9, 20)';
      ratings.innerHTML = '4.9/5.0';

      movieTitles[i].innerHTML = movieNames[y];
      movieTitles[i].appendChild(ratings);
      // movieTitles[i].style.fontSize = '2em';

      i++;
      y++;
      if (y === 4) { y = 0; }
    }

  }
}
