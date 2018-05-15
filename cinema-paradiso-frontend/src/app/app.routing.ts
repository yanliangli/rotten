import {Router, RouterModule, Routes, UrlSegment} from '@angular/router';
import {HomeComponent} from './global/home/home.component';
import {NgModule} from '@angular/core';
import {MovieDetailComponent} from './global/movie-detail/movie-detail.component';
import {CelebrityComponent} from './global/celebrity/celebrity.component';
import {RegUserComponent} from './user/reg-user/reg-user.component';
import {SearchComponent} from './global/search/search.component';
import {AdminComponent} from './user/admin/admin.component';
import {StarsComponent} from './global/stars/stars.component';
import {ContactComponent} from './global/general/contact/contact.component';
import {AboutComponent} from './global/general/about/about.component';
import {OpeningThisWeekComponent} from './global/categories/opening_this_week/opening_this_week.component';
import {ComingSoonComponent} from './global/categories/coming_soon/coming_soon.component';
import {TopBoxOfficeComponent} from './global/categories/top_box_office/top_box_office.component';
import {CertifiedFreshMovieComponent} from './global/categories/certified_fresh_movie/certified_fresh_movies.component';
import {CertifiedFreshTVComponent} from './global/categories/certified_fresh_tv/certified_fresh_tv.component';
import {MostPopularTVComponent} from './global/categories/most_popular_tv/most_popular_tv.component';
import {NewTVTonightComponent} from './global/categories/new_tv_tonight/new_tv_tonight.component';
import {TopRatedTVComponent} from './global/categories/top_rated_tv/top_rated_tv.component';
import {TopRatedMovieComponent} from './global/categories/top_rated_movies/top_rated_movie.component';
import {AcademyAwardComponent} from './global/categories/acadamy_award/academy_award.component';
import {SearchMovieTabComponent} from './global/search/movie-tab/movie-tab.component';
import {SearchTVTabComponent} from './global/search/tv-tab/tv-tab.component';
import {SearchPeopleTabComponent} from './global/search/people-tab/people-tab.component';
import {UserViewComponent} from './global/userView/userView.component';
import {CriticApplicationComponent} from './global/general/critic-applicaition/critic.component';
import {PolicyComponent} from './global/general/policy/policy.component';
import {CareerComponent} from './global/general/career/career.component';
import {MoviePhotosComponent} from './global/movie-detail/photos/movie-photos.component';
import {MovieTrailersComponent} from './global/movie-detail/trailers/movie-trailers.component';
import {MovieReviewsComponent} from './global/movie-detail/reviews/movie-reviews.component';

const appRoutes: Routes = [
  //{path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  //{path: 'movies', component: OpeningThisWeekComponent},
  {path: 'movies/opening_this_week',component: OpeningThisWeekComponent},
  {path: 'movies/coming_soon',component: ComingSoonComponent},
  {path: 'movies/top_box_office',component: TopBoxOfficeComponent},
  {path: 'movies/certified_fresh_movie',component: CertifiedFreshMovieComponent},
  {path: 'movies/new_tv_tonight',component: NewTVTonightComponent},
  {path: 'movies/most_popular_tv',component: MostPopularTVComponent},
  {path: 'movies/certified_fresh_tv',component: CertifiedFreshTVComponent},
  {path: 'movies/academy_award',component: AcademyAwardComponent},
  {path: 'movies/top_rated_movie',component: TopRatedMovieComponent},
  {path: 'movies/top_rated_tv',component: TopRatedTVComponent},
  {path: 'celebrity/:id', component: CelebrityComponent},
  {path: 'search', component: SearchComponent},
  {path: 'search/movie', component: SearchMovieTabComponent},
  {path: 'search/tv', component: SearchTVTabComponent},
  {path: 'search/celebrity', component: SearchPeopleTabComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'star', component: StarsComponent},
  {path: 'movie/:id', component: MovieDetailComponent},
  {path: 'movie/photos/:id', component: MoviePhotosComponent},
  {path: 'movie/trailers/:id', component: MovieTrailersComponent},
  {path: 'movie/reviews/:id', component: MovieReviewsComponent},
  {path: 'user/:id', component: RegUserComponent},
  {path: 'userView:id', component: UserViewComponent},
  {path: 'home/:id', component: MovieDetailComponent},
  {path: 'about', component: AboutComponent},
  {path: 'contact', component: ContactComponent},
  {path: 'policy', component: PolicyComponent},
  {path: 'critics', component: CriticApplicationComponent},
  {path: 'career', component: CareerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule{

}

