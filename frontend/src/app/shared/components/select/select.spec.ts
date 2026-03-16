import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Select } from './select';

describe('Select', () => {
  let component: Select;
  let fixture: ComponentFixture<Select>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Select]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Select);
    component = fixture.componentInstance;
    component.options = [
      { label: 'Open', value: 'open' },
      { label: 'Closed', value: 'closed' },
    ];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update selected value', async () => {
    const select: HTMLSelectElement = fixture.nativeElement.querySelector('select');
    select.value = select.options[1].value;
    select.dispatchEvent(new Event('change'));
    fixture.detectChanges();

    expect(component.value).toBe('open');
  });
});
