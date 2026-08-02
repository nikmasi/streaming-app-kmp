import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { SignUp } from './pages/sign-up/sign-up';
import { SignIn } from './pages/sign-in/sign-in';
import { NotFound } from './pages/not-found/not-found';
import { MovieDetails } from './pages/movie-details/movie-details';
import { Search } from './pages/search/search';
import { MyList } from './pages/my-list/my-list';
import { Watch } from './pages/watch/watch';
import { SignUpPassword } from './pages/sign-up-password/sign-up-password';
import { SignUpName } from './pages/sign-up-name/sign-up-name';
import { Profile } from './pages/profile/profile';
import { AdminPanel } from './admin/admin-panel/admin-panel';
import { AdminMovies } from './admin/admin-movies/admin-movies';
import { AdminAnalytics } from './admin/admin-analytics/admin-analytics';
import { AdminCategories } from './admin/admin-categories/admin-categories';
import { AdminTvshows } from './admin/admin-tvshows/admin-tvshows';
import { AdminSettings } from './admin/admin-settings/admin-settings';
import { AdminUsers } from './admin/admin-users/admin-users';
import { EditProfile } from './pages/edit-profile/edit-profile';

export const routes: Routes = [
    {path:"", component:SignUp},
    {path:"signIn", component:SignIn},
    {path:"home", component:HomeComponent},
    {path:"not-found", component:NotFound},
    {path:"movie-details", component:MovieDetails},
    {path:"search", component:Search},
    {path:"my-list", component:MyList},
    {path:"watch", component:Watch},
    {path:"sign-up-password", component: SignUpPassword},
    {path:"sign-up-name", component: SignUpName},
    {path:"profile", component: Profile},
    {path:"admin-panel", component: AdminPanel},
    {path:"admin-movies", component: AdminMovies},
    {path:"admin-analytics", component: AdminAnalytics},
    {path:"admin-categories", component: AdminCategories},
    {path:"admin-tvshows", component: AdminTvshows},
    {path:"admin-settings", component: AdminSettings},
    {path:"admin-users", component: AdminUsers},
    {path:"edit-profile", component: EditProfile},
];
