import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IconButton } from './icon-button';

describe('IconButton', () => {
  let component: IconButton;
  let fixture: ComponentFixture<IconButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IconButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IconButton);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set aria label', () => {
    fixture.componentRef.setInput('ariaLabel', 'Close dialog');
    fixture.detectChanges();

    const button: HTMLButtonElement = fixture.nativeElement.querySelector('button');
    expect(button.getAttribute('aria-label')).toBe('Close dialog');
  });
});
