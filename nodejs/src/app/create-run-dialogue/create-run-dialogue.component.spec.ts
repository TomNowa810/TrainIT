import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRunDialogueComponent } from './create-run-dialogue.component';

describe('CreateRunDialogueComponent', () => {
  let component: CreateRunDialogueComponent;
  let fixture: ComponentFixture<CreateRunDialogueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateRunDialogueComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateRunDialogueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
