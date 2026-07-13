import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicProductListComponent } from './public-product-list.component';

describe('PublicProductListComponent', () => {
  let component: PublicProductListComponent;
  let fixture: ComponentFixture<PublicProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PublicProductListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
