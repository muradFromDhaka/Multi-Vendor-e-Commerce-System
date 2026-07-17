import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorProductVariantListComponent } from './vendor-product-variant-list.component';

describe('VendorProductVariantListComponent', () => {
  let component: VendorProductVariantListComponent;
  let fixture: ComponentFixture<VendorProductVariantListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorProductVariantListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorProductVariantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
