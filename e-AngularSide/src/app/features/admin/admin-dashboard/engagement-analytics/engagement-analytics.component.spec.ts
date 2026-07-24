import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EngagementAnalyticsComponent } from './engagement-analytics.component';

describe('EngagementAnalyticsComponent', () => {
  let component: EngagementAnalyticsComponent;
  let fixture: ComponentFixture<EngagementAnalyticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EngagementAnalyticsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EngagementAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
