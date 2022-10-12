import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentEditFormComponent } from './student-edit-form.component';

describe('StudentEditFormComponent', () => {
  let component: StudentEditFormComponent;
  let fixture: ComponentFixture<StudentEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentEditFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
