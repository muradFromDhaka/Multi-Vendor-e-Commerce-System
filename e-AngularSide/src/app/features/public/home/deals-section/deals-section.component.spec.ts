import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DealsSectionComponent } from './deals-section.component';

describe('DealsSectionComponent', () => {
  let component: DealsSectionComponent;
  let fixture: ComponentFixture<DealsSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DealsSectionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DealsSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
