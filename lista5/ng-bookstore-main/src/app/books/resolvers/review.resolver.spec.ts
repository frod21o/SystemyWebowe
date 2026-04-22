import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { reviewResolver } from './review.resolver';
import {Review} from "../model/review";

describe('reviewResolver', () => {
  const executeResolver: ResolveFn<Review[]> = (...resolverParameters) =>
      TestBed.runInInjectionContext(() => reviewResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
