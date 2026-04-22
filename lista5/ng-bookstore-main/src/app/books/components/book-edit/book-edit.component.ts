import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import {BooksService} from "../../services/books.service";
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'bs-book-edit',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './book-edit.component.html',
  styleUrl: './book-edit.component.scss'
})
export class BookEditComponent {

  form!: FormGroup;
  book: any;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private booksService: BooksService,
    private router: Router
  ) {}

  ngOnInit() {
    this.book = this.route.snapshot.data['book'];

    this.form = this.fb.group({
      title: [this.book.title, [Validators.required, Validators.maxLength(50)]],
      author: [this.book.author, [Validators.required, Validators.maxLength(50)]],
      year: [this.book.year, [
        Validators.required,
        Validators.min(1000),
        Validators.max(2023)
      ]],
      description: [this.book.description, [Validators.maxLength(1000)]]
    });
  }

  save() {
    if (this.form.invalid) return;

    this.booksService.saveBook(this.book.id, this.form.value)
      .subscribe(() => {
        this.router.navigate(['/books']);
      });
  }

  cancel() {
    this.router.navigate(['/books']);
  }
}
