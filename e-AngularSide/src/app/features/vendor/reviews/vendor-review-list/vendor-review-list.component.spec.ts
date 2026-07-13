import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorReviewListComponent } from './vendor-review-list.component';

describe('VendorReviewListComponent', () => {
  let component: VendorReviewListComponent;
  let fixture: ComponentFixture<VendorReviewListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorReviewListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorReviewListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
