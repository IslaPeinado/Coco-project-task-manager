import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { authInterceptor } from './auth-interceptor';
import { TokenService } from '../services/token/token.service';

describe('authInterceptor', () => {
  let httpTestingController: HttpTestingController;
  let http: HttpClient;
  let tokenService: TokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(withInterceptors([authInterceptor])), provideHttpClientTesting()],
    });
    http = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    tokenService = TestBed.inject(TokenService);
    localStorage.clear();
  });

  afterEach(() => {
    httpTestingController.verify();
    localStorage.clear();
  });

  it('adds bearer token when access token exists', () => {
    tokenService.setAccessToken('jwt-test-token');

    http.get('/api/projects').subscribe();

    const req = httpTestingController.expectOne('/api/projects');
    expect(req.request.headers.get('Authorization')).toBe('Bearer jwt-test-token');
    req.flush({});
  });

  it('does not set authorization header when there is no token', () => {
    http.get('/api/projects').subscribe();

    const req = httpTestingController.expectOne('/api/projects');
    expect(req.request.headers.has('Authorization')).toBeFalse();
    req.flush({});
  });

  it('keeps existing authorization header unchanged', () => {
    tokenService.setAccessToken('jwt-test-token');

    http.get('/api/projects', {
      headers: {
        Authorization: 'Basic abc123',
      },
    }).subscribe();

    const req = httpTestingController.expectOne('/api/projects');
    expect(req.request.headers.get('Authorization')).toBe('Basic abc123');
    req.flush({});
  });
});
