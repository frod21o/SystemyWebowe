import { ResolveFn } from '@angular/router';
import {Book} from "../model/book";
import {BooksService} from "../services/books.service";
import {inject} from "@angular/core";

export const bookResolver: ResolveFn<Book> = (route, state) => {
  const id = route.paramMap.get('bookId');
  return inject(BooksService).findBookById(Number(id));
};
