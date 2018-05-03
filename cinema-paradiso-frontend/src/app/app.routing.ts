import {Router, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './global/home/home.component';
import {NgModule} from '@angular/core';
import {CategoriesComponent} from './global/categories/categories.component';
import {MovieDetailComponent} from './global/movie-detail/movie-detail.component';
import {CelebrityComponent} from './global/celebrity/celebrity.component';
import {RegUserComponent} from './user/reg-user/reg-user.component';
import {SearchComponent} from './global/search/search.component';
import {AdminComponent} from './user/admin/admin.component';
import {StarsComponent} from './global/stars/stars.component';
import {ContactComponent} from './global/general/contact.component';
import {AboutComponent} from './global/general/about.component';

const appRoutes: Routes = [
  //{path: '', pathMatch: 'full', redirectTo: 'home'},
  {path: 'home', component: HomeComponent},
  {path: 'movies', component: CategoriesComponent},
  {path: 'celebrity/:id', component: CelebrityComponent},
  {path: 'search', component: SearchComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'star', component: StarsComponent},
  {path: 'movie/:id', component: MovieDetailComponent},
  {path: 'user/:id', component: RegUserComponent},
  {path: 'home/:id', component: MovieDetailComponent},
  {path:'about', component:AboutComponent},
  {path:'contact', component:ContactComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
