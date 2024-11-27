import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostTaskComponent } from './components/post-task/post-task.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DemoAngularMaterailModule } from 'src/app/DemoAngularMaterialModule';
import { UpdateTaskComponent } from './components/update-task/update-task.component';
import { ViewTaskDetailsComponent } from './components/view-task-details/view-task-details.component';
import { ViewEmployeeComponent } from './components/view-employee/view-employee.component';
import { AddEmployeeComponent } from './components/add-employee/add-employee.component';
import { ViewEmployeeDetailsComponent } from './components/view-employee-details/view-employee-details.component';
import { ViewEmployeeTasksComponent } from './components/view-employee-tasks/view-employee-tasks.component';


@NgModule({
  declarations: [
    DashboardComponent,
    PostTaskComponent,
    UpdateTaskComponent,
    ViewTaskDetailsComponent,
    ViewEmployeeComponent,
    AddEmployeeComponent,
    ViewEmployeeDetailsComponent,
    ViewEmployeeTasksComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    DemoAngularMaterailModule
  ]
})
export class AdminModule { }
