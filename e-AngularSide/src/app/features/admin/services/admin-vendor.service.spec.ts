import { TestBed } from '@angular/core/testing';

import { AdminVendorService } from './admin-vendor.service';

describe('AdminVendorService', () => {
  let service: AdminVendorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminVendorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
