import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

export interface TableColumn {
  key: string;
  label: string;
  align?: 'left' | 'center' | 'right';
}

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './table.html',
  styleUrl: './table.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Table {
  @Input() columns: TableColumn[] = [];
  @Input() rows: Record<string, string | number>[] = [];
  @Input() emptyTitle = 'No data yet';
  @Input() emptyDescription = 'There is nothing to show right now.';

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-table-host';
  }
}
