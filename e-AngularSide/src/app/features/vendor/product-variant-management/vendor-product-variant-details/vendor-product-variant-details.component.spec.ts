import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorProductVariantDetailsComponent } from './vendor-product-variant-details.component';

describe('VendorProductVariantDetailsComponent', () => {
  let component: VendorProductVariantDetailsComponent;
  let fixture: ComponentFixture<VendorProductVariantDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorProductVariantDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorProductVariantDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
