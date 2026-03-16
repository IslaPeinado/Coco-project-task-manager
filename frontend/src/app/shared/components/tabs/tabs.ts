import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, HostBinding, Input, Output } from '@angular/core';

export interface TabItem {
  id: string;
  label: string;
  disabled?: boolean;
  badge?: string;
}

@Component({
  selector: 'app-tabs',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tabs.html',
  styleUrl: './tabs.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Tabs {
  @Input() items: TabItem[] = [];
  @Input() activeId = '';
  @Input() stretch = false;
  @Output() activeIdChange = new EventEmitter<string>();

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-tabs-host';
  }

  setActive(id: string, disabled?: boolean): void {
    if (disabled) {
      return;
    }

    this.activeId = id;
    this.activeIdChange.emit(id);
  }
}
