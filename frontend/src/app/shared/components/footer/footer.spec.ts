import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Footer } from './footer';

describe('Footer', () => {
  let component: Footer;
  let fixture: ComponentFixture<Footer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Footer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Footer);
    component = fixture.componentInstance;
    component.sections = [
      {
        title: 'Product',
        links: [{ label: 'Projects' }, { label: 'Tasks' }],
      },
    ];
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render section titles', () => {
    const title = fixture.nativeElement.querySelector('.coco-footer__section-title');
    expect(title.textContent).toContain('Product');
  });
});
