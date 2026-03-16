import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-stat-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './stat-card.html',
  styleUrl: './stat-card.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StatCard {
  @Input() label = '';
  @Input() value = '';
  @Input() trend = '';
  @Input() tone: 'primary' | 'success' | 'warning' | 'danger' = 'primary';
  @Input() icon = '';

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-stat-card-host';
  }
}
