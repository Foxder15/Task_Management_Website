import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view-employee-tasks',
  templateUrl: './view-employee-tasks.component.html',
  styleUrls: ['./view-employee-tasks.component.scss']
})
export class ViewEmployeeTasksComponent {

  id: number = this.route.snapshot.params["id"];

  listOfTasks: any = []
  searchForm!:FormGroup;
  
  constructor(private route: ActivatedRoute,
    private fb: FormBuilder,
    private adminService: AdminService,
    private snackBar: MatSnackBar,
    private router: Router
  ) { 
    this.getTaskById();
  }

  getTaskById() {
    this.adminService.getTasksByUserId(this.id).subscribe((res) => {
      this.listOfTasks = res;
    })
  }

  deleteTask(id: number) {
    this.adminService.deleteTask(id).subscribe((res) => {
      this.snackBar.open("Task deleted successfully", "Close", { duration: 5000});
      // this.getTasks();
    })
  }
}
