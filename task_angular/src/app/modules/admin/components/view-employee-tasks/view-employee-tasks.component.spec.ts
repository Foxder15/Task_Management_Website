import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmployeeTasksComponent } from './view-employee-tasks.component';

describe('ViewEmployeeTasksComponent', () => {
  let component: ViewEmployeeTasksComponent;
  let fixture: ComponentFixture<ViewEmployeeTasksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewEmployeeTasksComponent]
    });
    fixture = TestBed.createComponent(ViewEmployeeTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
