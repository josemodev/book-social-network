import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnsBooksComponent } from './returns-books.component';

describe('ReturnsBooksComponent', () => {
  let component: ReturnsBooksComponent;
  let fixture: ComponentFixture<ReturnsBooksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReturnsBooksComponent]
    });
    fixture = TestBed.createComponent(ReturnsBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
