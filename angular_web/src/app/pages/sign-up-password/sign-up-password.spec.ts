import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpPassword } from './sign-up-password';

describe('SignUpPassword', () => {
  let component: SignUpPassword;
  let fixture: ComponentFixture<SignUpPassword>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignUpPassword]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignUpPassword);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
