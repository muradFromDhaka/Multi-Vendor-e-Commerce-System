import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorCustomerListComponent } from './vendor-customer-list.component';

describe('VendorCustomerListComponent', () => {
  let component: VendorCustomerListComponent;
  let fixture: ComponentFixture<VendorCustomerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorCustomerListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorCustomerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
