import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../service/auth';

export const adminAuthGuardGuard: CanActivateFn = (route, state) => {

  const authService = inject(Auth);
  let router = inject(Router)

  if(authService.isAdmin()){
    return true;
  }
  else if(authService.isSignIn()){
    router.navigate(['/home']);
    return false;
  }
  else{
    router.navigate(['/signIn']);
    return false;
  }
};
