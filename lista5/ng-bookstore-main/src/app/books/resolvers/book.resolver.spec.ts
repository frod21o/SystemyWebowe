import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { BookResolver } from './book.resolver';
import {Book} from "../model/book";

describe('bookResolver', () => {
  const executeResolver: ResolveFn<Book> = (...resolverParameters) =>
      TestBed.runInInjectionContext(() => BookResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
