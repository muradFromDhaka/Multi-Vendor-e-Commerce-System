import { TestBed } from '@angular/core/testing';

import { VendorProductVariantService } from './vendor-product-variant.service';

describe('VendorProductVariantService', () => {
  let service: VendorProductVariantService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorProductVariantService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
