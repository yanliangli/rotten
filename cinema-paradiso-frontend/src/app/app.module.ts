import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {NgxPaginationModule} from 'ngx-pagination'; // <-- import the module
import {HomeComponent} from './global/home/home.component';
import {FooterComponent} from './global/footer/footer.component';
import {HeaderComponent} from './global/header/header.component';
import {AppRoutingModule} from './app.routing';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoginComponent} from './global/login/login.component';
import {MovieDetailComponent} from './global/movie-detail/movie-detail.component';
import {CelebrityComponent} from './global/celebrity/celebrity.component';
import {RegUserComponent} from './user/reg-user/reg-user.component';
import {AdminComponent} from './user/admin/admin.component';
import {LoginStatusService} from './global/login/login.status.service';
import {AuthInterceptor} from './http.interceptor';
import {HomeService} from './global/home/home.service';
import {MovieDetailService} from './global/movie-detail/movie-detail.service';
import {MovieService} from './global/movie/movie.service';
import {ToastrModule} from 'ngx-toastr';
import {StarsComponent} from './global/stars/stars.component';
import {AboutComponent} from './global/general/about/about.component';
import {ContactComponent} from './global/general/contact/contact.component';
import {PolicyComponent} from './global/general/policy/policy.component';
import {SidebarComponent} from './global/categories/sidebar/sidebar.component';
import {OpeningThisWeekComponent} from './global/categories/opening_this_week/opening_this_week.component';
import {ComingSoonComponent} from './global/categories/coming_soon/coming_soon.component';
import {TopBoxOfficeComponent} from './global/categories/top_box_office/top_box_office.component';
import {CertifiedFreshMovieComponent} from './global/categories/certified_fresh_movie/certified_fresh_movies.component';
import {CertifiedFreshTVComponent} from './global/categories/certified_fresh_tv/certified_fresh_tv.component';
import {MostPopularTVComponent} from './global/categories/most_popular_tv/most_popular_tv.component';
import {NewTVTonightComponent} from './global/categories/new_tv_tonight/new_tv_tonight.component';
import {TopRatedTVComponent} from './global/categories/top_rated_tv/top_rated_tv.component';
import {AcademyAwardComponent} from './global/categories/acadamy_award/academy_award.component';
import {TopRatedMovieComponent} from './global/categories/top_rated_movies/top_rated_movie.component';
import {SearchComponent} from './global/search/search.component';
import {SearchToolbarComponent} from './global/search/search-toolbar/search-toolbar.component';
import {SearchMovieTabComponent} from './global/search/movie-tab/movie-tab.component';
import {SearchTVTabComponent} from './global/search/tv-tab/tv-tab.component';
import {SearchPeopleTabComponent} from './global/search/people-tab/people-tab.component';
import {SearchService} from './global/search/search.service';
import {UserViewComponent} from './global/userView/userView.component';
import {CriticApplicationComponent} from './global/general/critic-applicaition/critic.component';
import {CareerComponent} from './global/general/career/career.component';

@NgModule({
  // entryComponents: [MessageComponent],
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    HeaderComponent,
    LoginComponent,
    MovieDetailComponent,
    CelebrityComponent,
    RegUserComponent,
    UserViewComponent,
    SearchComponent,
    SearchToolbarComponent,
    SearchMovieTabComponent,
    SearchPeopleTabComponent,
    SearchTVTabComponent,
    AdminComponent,
    StarsComponent,
    AboutComponent,
    ContactComponent,
    PolicyComponent,
    SidebarComponent,
    OpeningThisWeekComponent,
    ComingSoonComponent,
    TopBoxOfficeComponent,
    CertifiedFreshMovieComponent,
    CertifiedFreshTVComponent,
    MostPopularTVComponent,
    NewTVTonightComponent,
    TopRatedTVComponent,
    TopRatedMovieComponent,
    AcademyAwardComponent,
    CriticApplicationComponent,
    CareerComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    NgbModule.forRoot(),
    CommonModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgxPaginationModule,
  ],
  providers: [
    LoginStatusService,
    HomeService,
    MovieService,
    MovieDetailService,
    SearchService,
    {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
