import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-empty-state',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empty-state.html',
  styleUrl: './empty-state.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EmptyState {
  @Input() title = '';
  @Input() description = '';
  @Input() icon = '';
  @Input() compact = false;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-empty-state-host';
  }
}
