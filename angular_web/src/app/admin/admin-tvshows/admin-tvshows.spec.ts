import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTvshows } from './admin-tvshows';

describe('AdminTvshows', () => {
  let component: AdminTvshows;
  let fixture: ComponentFixture<AdminTvshows>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTvshows]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminTvshows);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
