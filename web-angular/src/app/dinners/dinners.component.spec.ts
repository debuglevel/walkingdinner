import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DinnersComponent } from './dinners.component';

describe('DinnersComponent', () => {
  let component: DinnersComponent;
  let fixture: ComponentFixture<DinnersComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DinnersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DinnersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
