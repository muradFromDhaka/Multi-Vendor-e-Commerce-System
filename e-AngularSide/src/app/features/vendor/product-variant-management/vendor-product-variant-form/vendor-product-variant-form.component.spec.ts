import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorProductVariantFormComponent } from './vendor-product-variant-form.component';

describe('VendorProductVariantFormComponent', () => {
  let component: VendorProductVariantFormComponent;
  let fixture: ComponentFixture<VendorProductVariantFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorProductVariantFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorProductVariantFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
