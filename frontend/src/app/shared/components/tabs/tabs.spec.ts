import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tabs } from './tabs';

describe('Tabs', () => {
  let component: Tabs;
  let fixture: ComponentFixture<Tabs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Tabs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Tabs);
    component = fixture.componentInstance;
    component.items = [
      { id: 'details', label: 'Details' },
      { id: 'activity', label: 'Activity' },
    ];
    component.activeId = 'details';
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit active tab changes', () => {
    spyOn(component.activeIdChange, 'emit');
    component.setActive('activity');
    expect(component.activeIdChange.emit).toHaveBeenCalledWith('activity');
  });
});
