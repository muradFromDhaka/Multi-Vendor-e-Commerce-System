import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorProductFormComponent } from './vendor-product-form.component';

describe('VendorProductFormComponent', () => {
  let component: VendorProductFormComponent;
  let fixture: ComponentFixture<VendorProductFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorProductFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorProductFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
