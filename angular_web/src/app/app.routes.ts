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
import { AdminUploadVideo } from './admin/admin-upload-video/admin-upload-video';
import { adminAuthGuardGuard } from './guards/admin-auth-guard-guard';
import { userGuard } from './guards/user-guard-guard';
import { guestGuard } from './guards/guest-guard';
import { WatchHistory } from './pages/watch-history/watch-history';

export const routes: Routes = [
    {path:"", component:SignUp, canActivate: [guestGuard]},
    {path:"signIn", component:SignIn, canActivate: [guestGuard]},
    {path:"home", component:HomeComponent, canActivate: [userGuard]},
    {path:"not-found", component:NotFound},
    {path:"movie-details/:id", component:MovieDetails, canActivate: [userGuard]},
    {path:"movie-details", component:MovieDetails, canActivate: [userGuard]},
    {path:"search", component:Search, canActivate: [userGuard]},
    {path:"my-list", component:MyList, canActivate: [userGuard]},
    {path:"watch", component:Watch, canActivate: [userGuard]},
    {path:"sign-up-password", component: SignUpPassword, canActivate: [guestGuard]},
    {path:"sign-up-name", component: SignUpName, canActivate: [guestGuard]},
    {path:"profile", component: Profile, canActivate: [userGuard]},
    {path:"admin-panel", component: AdminPanel, canActivate: [adminAuthGuardGuard]},
    {path:"admin-movies", component: AdminMovies, canActivate: [adminAuthGuardGuard]},
    {path:"admin-analytics", component: AdminAnalytics, canActivate: [adminAuthGuardGuard]},
    {path:"admin-categories", component: AdminCategories, canActivate: [adminAuthGuardGuard]},
    {path:"admin-tvshows", component: AdminTvshows, canActivate: [adminAuthGuardGuard]},
    {path:"admin-settings", component: AdminSettings, canActivate: [adminAuthGuardGuard]},
    {path:"admin-users", component: AdminUsers, canActivate: [adminAuthGuardGuard]},
    {path:"edit-profile", component: EditProfile, canActivate: [userGuard]},
    {path:"admin-upload-video", component: AdminUploadVideo, canActivate: [adminAuthGuardGuard]},
    {path:"watch-history", component: WatchHistory, canActivate: [userGuard]},
];
