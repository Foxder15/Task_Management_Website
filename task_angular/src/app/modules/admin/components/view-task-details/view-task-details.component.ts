import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-task-details',
  templateUrl: './view-task-details.component.html',
  styleUrls: ['./view-task-details.component.scss']
})
export class ViewTaskDetailsComponent {
  
  taskId: number = this.activedRoute.snapshot.params["id"];
  taskData: any;
  comments: any;
  commnetForm!: FormGroup;

  constructor(private service: AdminService,
    private activedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {

  }

  ngOnInit() {
    this.getTaskById();
    this.getComments();
    this.commnetForm = this.fb.group({
      content: [null, Validators.required]
    })
  }


  getTaskById() {
    this.service.getTaskById(this.taskId).subscribe((res) => {
      this.taskData = res;
    })
  }

  getComments() {
    this.service.getCommentByTask(this.taskId).subscribe((res) => {
      this.comments = res;
    })
  }

  publishComment() {
    this.service.createComment(this.taskId, this.commnetForm.get("content")?.value).subscribe((res) => {
      if (res.id != null) {
        this.snackBar.open("Comment successfully", "Close", { duration: 5000 });
        this.getComments();
      } else {
        this.snackBar.open("Something went wrong", "Close", { duration: 5000 });
      }
    })
  }

  
}
