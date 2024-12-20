import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  listOfTasks: any = []
  searchForm!:FormGroup;

  constructor(private service: AdminService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
  ) {
    this.getTasks();
    this.searchForm = this.fb.group({
      title: [null]
    })
  }

  getTasks() {
    this.service.getTasks().subscribe((res) => {
      this.listOfTasks = res;
    })
  }

  deleteTask(id: number) {
    this.service.deleteTask(id).subscribe((res) => {
      this.snackBar.open("Task deleted successfully", "Close", { duration: 5000});
      this.getTasks();
    })
  }

  searchTask() {
   
    const title = this.searchForm.get('title')!.value;
    console.log(title)
    if (title == '') {
      this.getTasks();
    }
    this.service.searchTask(title).subscribe((res) => {
      console.log(res)
      this.listOfTasks = res;
    })
  }
}
