import { TestBed } from '@angular/core/testing';

import { VendorCustomerService } from './vendor-customer.service';

describe('VendorCustomerService', () => {
  let service: VendorCustomerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorCustomerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
