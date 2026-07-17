import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorCustomerDetailsComponent } from './vendor-customer-details.component';

describe('VendorCustomerDetailsComponent', () => {
  let component: VendorCustomerDetailsComponent;
  let fixture: ComponentFixture<VendorCustomerDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorCustomerDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorCustomerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
