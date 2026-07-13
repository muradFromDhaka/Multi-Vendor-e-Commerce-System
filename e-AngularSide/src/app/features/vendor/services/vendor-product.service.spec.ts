import { TestBed } from '@angular/core/testing';

import { VendorProductService } from './vendor-product.service';

describe('VendorProductService', () => {
  let service: VendorProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
