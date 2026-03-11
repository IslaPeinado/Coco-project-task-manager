import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Navbar } from './navbar';

describe('Navbar', () => {
  let component: Navbar;
  let fixture: ComponentFixture<Navbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Navbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Navbar);
    component = fixture.componentInstance;
    component.links = [
      { id: 'dashboard', label: 'Dashboard', icon: 'grid_view' },
      { id: 'projects', label: 'Projects', icon: 'folder_open' },
    ];
    component.actions = [
      { id: 'notifications', icon: 'notifications', ariaLabel: 'Notifications', badge: true },
    ];
    component.activeLinkId = 'dashboard';
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit active link changes', () => {
    spyOn(component.activeLinkIdChange, 'emit');

    component.onLinkClick(component.links[1]);

    expect(component.activeLinkIdChange.emit).toHaveBeenCalledWith('projects');
  });

  it('should emit search changes', () => {
    spyOn(component.searchValueChange, 'emit');

    component.onSearch('roadmap');

    expect(component.searchValueChange.emit).toHaveBeenCalledWith('roadmap');
  });

  it('should emit collapsed changes when collapsible', () => {
    spyOn(component.collapsedChange, 'emit');
    component.collapsible = true;

    component.toggleCollapsed();

    expect(component.collapsed).toBeTrue();
    expect(component.collapsedChange.emit).toHaveBeenCalledWith(true);
  });
});
