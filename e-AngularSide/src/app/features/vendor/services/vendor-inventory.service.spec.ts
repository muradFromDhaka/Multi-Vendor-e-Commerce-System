import { TestBed } from '@angular/core/testing';

import { VendorInventoryService } from './vendor-inventory.service';

describe('VendorInventoryService', () => {
  let service: VendorInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorInventoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
