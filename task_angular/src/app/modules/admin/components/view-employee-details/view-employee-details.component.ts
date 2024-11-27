import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-employee-details',
  templateUrl: './view-employee-details.component.html',
  styleUrls: ['./view-employee-details.component.scss']
})
export class ViewEmployeeDetailsComponent {

  id: number = this.route.snapshot.params["id"];
  updateUserForm!: FormGroup
  listOfRoles: any = ["EMPLOYEE", "TESTER", "DEVELOPER"];

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private adminService: AdminService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.getUserById();
    this.updateUserForm= this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      userRole: [null, [Validators.required]],
    })
  }

  getUserById() {
    this.adminService.getUserById(this.id).subscribe((res) => {
      this.updateUserForm.patchValue(res);
    })
  }

  updateUser() {
    this.adminService.updateUser(this.id, this.updateUserForm.value).subscribe((res) => {
      console.log(res)
      if(res.id != null) {
        this.snackBar.open("Task updated sucessfully", "Close", {duration:5000});
        this.router.navigateByUrl("/admin/employee");
      }
    })
    this.snackBar.open("Email already existed", "Failed", {duration:5000});

  }
}
