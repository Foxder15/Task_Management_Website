import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.component.html',
  styleUrls: ['./view-employee.component.scss']
})
export class ViewEmployeeComponent {
  listOfEmployees: any = [];
  searchForm!:FormGroup;

  constructor(private service: AdminService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
  ) {
      this.getEmployees();
  }

  getEmployees() {
    this.service.getUsers().subscribe((res) => {
      console.log(res)
      this.listOfEmployees = res
    })
  }

  deleteUser(id: number) {
    this.service.deleteUser(id).subscribe((res) => {
      this.snackBar.open("User deleted successfully", "Close", { duration: 5000});
      this.getEmployees();
    })
  }
}
