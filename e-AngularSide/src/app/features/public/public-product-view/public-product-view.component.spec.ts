import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicProductViewComponent } from './public-product-view.component';

describe('PublicProductViewComponent', () => {
  let component: PublicProductViewComponent;
  let fixture: ComponentFixture<PublicProductViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicProductViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicProductViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
