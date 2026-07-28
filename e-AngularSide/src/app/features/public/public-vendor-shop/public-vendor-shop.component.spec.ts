import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicVendorShopComponent } from './public-vendor-shop.component';

describe('PublicVendorShopComponent', () => {
  let component: PublicVendorShopComponent;
  let fixture: ComponentFixture<PublicVendorShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicVendorShopComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicVendorShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
