import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../service/auth';

export const guestGuard: CanActivateFn = () => {
  const authService = inject(Auth);
  const router = inject(Router);

  if (authService.isSignIn()) {
    router.navigate(['/not-found']);
    return false;
  }
  else{
    return true;
  }
 
};
