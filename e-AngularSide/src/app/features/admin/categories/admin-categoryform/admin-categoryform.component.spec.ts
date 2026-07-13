import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCategoryformComponent } from './admin-categoryform.component';

describe('AdminCategoryformComponent', () => {
  let component: AdminCategoryformComponent;
  let fixture: ComponentFixture<AdminCategoryformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCategoryformComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCategoryformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
