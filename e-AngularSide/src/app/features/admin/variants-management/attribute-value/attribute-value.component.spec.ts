import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributeValueComponent } from './attribute-value.component';

describe('AttributeValueComponent', () => {
  let component: AttributeValueComponent;
  let fixture: ComponentFixture<AttributeValueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AttributeValueComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttributeValueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
