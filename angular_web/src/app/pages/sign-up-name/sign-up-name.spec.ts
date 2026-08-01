import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpName } from './sign-up-name';

describe('SignUpName', () => {
  let component: SignUpName;
  let fixture: ComponentFixture<SignUpName>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignUpName]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignUpName);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
