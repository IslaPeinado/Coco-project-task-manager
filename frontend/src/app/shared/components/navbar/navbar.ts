import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, HostBinding, Input, Output } from '@angular/core';

import { Avatar } from '../avatar/avatar';

export interface NavbarLinkItem {
  id: string;
  label: string;
  href?: string;
  icon?: string;
  badge?: string;
  disabled?: boolean;
}

export interface NavbarActionItem {
  id: string;
  icon: string;
  ariaLabel: string;
  badge?: boolean;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, Avatar],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Navbar {
  @Input() brand = 'COCO';
  @Input() brandCaption = 'Management';
  @Input() searchPlaceholder = 'Search projects or tasks';
  @Input() searchValue = '';
  @Input() showSearch = true;
  @Input() links: NavbarLinkItem[] = [];
  @Input() activeLinkId = '';
  @Input() actions: NavbarActionItem[] = [];
  @Input() userName = '';
  @Input() userRole = '';
  @Input() userAvatarSrc = '';
  @Input() userAvatarAlt = 'User avatar';
  @Input() sticky = true;
  @Input() compact = false;
  @Input() collapsible = false;
  @Input() collapsed = false;
  @Input() collapseAriaLabel = 'Collapse navigation';
  @Input() expandAriaLabel = 'Expand navigation';

  @Output() activeLinkIdChange = new EventEmitter<string>();
  @Output() searchValueChange = new EventEmitter<string>();
  @Output() actionClick = new EventEmitter<string>();
  @Output() userClick = new EventEmitter<void>();
  @Output() menuToggle = new EventEmitter<boolean>();
  @Output() collapsedChange = new EventEmitter<boolean>();

  mobileMenuOpen = false;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-navbar-host';
  }

  @HostBinding('class.coco-navbar-host--sticky')
  get isSticky(): boolean {
    return this.sticky;
  }

  @HostBinding('class.coco-navbar-host--collapsed')
  get isCollapsed(): boolean {
    return this.collapsed;
  }

  isActive(link: NavbarLinkItem): boolean {
    return !!this.activeLinkId && link.id === this.activeLinkId;
  }

  onSearch(value: string): void {
    this.searchValue = value;
    this.searchValueChange.emit(value);
  }

  onLinkClick(link: NavbarLinkItem): void {
    if (link.disabled) {
      return;
    }

    this.activeLinkId = link.id;
    this.activeLinkIdChange.emit(link.id);
    this.mobileMenuOpen = false;
    this.menuToggle.emit(this.mobileMenuOpen);
  }

  onActionClick(actionId: string): void {
    this.actionClick.emit(actionId);
  }

  onUserClick(): void {
    this.userClick.emit();
  }

  toggleMobileMenu(): void {
    this.mobileMenuOpen = !this.mobileMenuOpen;
    this.menuToggle.emit(this.mobileMenuOpen);
  }

  toggleCollapsed(): void {
    if (!this.collapsible) {
      return;
    }

    this.collapsed = !this.collapsed;
    this.collapsedChange.emit(this.collapsed);
  }
}
