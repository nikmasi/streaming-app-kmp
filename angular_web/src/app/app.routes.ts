import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { SignUp } from './pages/sign-up/sign-up';
import { SignIn } from './pages/sign-in/sign-in';
import { NotFound } from './pages/not-found/not-found';
import { MovieDetails } from './pages/movie-details/movie-details';
import { Search } from './pages/search/search';
import { MyList } from './pages/my-list/my-list';
import { Watch } from './pages/watch/watch';

export const routes: Routes = [
    {path:"", component:SignUp},
    {path:"signIn", component:SignIn},
    {path:"home", component:HomeComponent},
    {path:"not-found", component:NotFound},
    {path:"movie-details", component:MovieDetails},
    {path:"search", component:Search},
    {path:"my-list", component:MyList},
    {path:"watch", component:Watch}
];
