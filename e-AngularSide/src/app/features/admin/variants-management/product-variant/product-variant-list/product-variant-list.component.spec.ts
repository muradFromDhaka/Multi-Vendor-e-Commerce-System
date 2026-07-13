import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductVariantListComponent } from './product-variant-list.component';

describe('ProductVariantListComponent', () => {
  let component: ProductVariantListComponent;
  let fixture: ComponentFixture<ProductVariantListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductVariantListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductVariantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
