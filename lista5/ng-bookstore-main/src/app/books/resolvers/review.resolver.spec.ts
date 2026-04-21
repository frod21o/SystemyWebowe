import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { reviewResolver } from './review.resolver';

describe('reviewResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => reviewResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
