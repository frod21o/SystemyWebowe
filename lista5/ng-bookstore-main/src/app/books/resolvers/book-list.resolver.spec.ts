import { TestBed } from '@angular/core/testing';
import {ResolveFn} from "@angular/router";

describe('BookListResolver', () => {
  const bookListResolver: ResolveFn<boolean> = (...resolverParameters) =>
    TestBed.runInInjectionContext(() => bookListResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(bookListResolver).toBeTruthy();
  });
});
