import { Component } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss']
})
export class AddEmployeeComponent {
  signupForm!: FormGroup;
  listOfRoles: any = ["EMPLOYEE", "TESTER", "DEVELOPER"];

  hidePassword = true;

  constructor(private fb: FormBuilder,
    private adminService: AdminService,
    private snackbar: MatSnackBar,
    private router:Router
  ) {
    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      userRole: [null, [Validators.required]],
      password: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required]],
    })
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  onSubmit() {
    console.log(this.signupForm.value)
    const password = this.signupForm.get("password")?.value;
    const confirmPassword = this.signupForm.get("confirmPassword")?.value;

    if (password !== confirmPassword) {
      this.snackbar.open("Password do not match", "Close", { duration: 5000, panelClass: "error-snackbar"});
      return;
    }

    this.adminService.postUser(this.signupForm.value).subscribe((res) => {
      console.log(res)

      if (res.id != null) {
        this.snackbar.open("Add user successfully", "Close", { duration: 5000});
        this.router.navigateByUrl("/admin/employee");
      } 
    })
    this.snackbar.open("Add failed, may User already existed", "Close", { duration: 5000, panelClass: "error-snackbar"})
  }
}
