import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUploadVideo } from './admin-upload-video';

describe('AdminUploadVideo', () => {
  let component: AdminUploadVideo;
  let fixture: ComponentFixture<AdminUploadVideo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminUploadVideo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminUploadVideo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
