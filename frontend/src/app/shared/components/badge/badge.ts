import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-badge',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './badge.html',
  styleUrl: './badge.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Badge {
  @Input() variant: 'neutral' | 'success' | 'warning' | 'danger' | 'primary' = 'neutral';
  @Input() size: 'sm' | 'md' = 'md';

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-badge-host';
  }
}
