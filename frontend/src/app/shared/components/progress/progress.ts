import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-progress',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './progress.html',
  styleUrl: './progress.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Progress {
  @Input() value = 0;
  @Input() max = 100;
  @Input() showLabel = true;
  @Input() tone: 'primary' | 'success' | 'warning' = 'primary';

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-progress-host';
  }

  get percentage(): number {
    if (this.max <= 0) {
      return 0;
    }

    return Math.min(100, Math.max(0, (this.value / this.max) * 100));
  }
}
