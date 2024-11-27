import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostTaskComponent } from './components/post-task/post-task.component';
import { UpdateTaskComponent } from './components/update-task/update-task.component';
import { ViewTaskDetailsComponent } from './components/view-task-details/view-task-details.component';
import { ViewEmployeeComponent } from './components/view-employee/view-employee.component';
import { AddEmployeeComponent } from './components/add-employee/add-employee.component';
import { ViewEmployeeDetailsComponent } from './components/view-employee-details/view-employee-details.component';
import { ViewEmployeeTasksComponent } from './components/view-employee-tasks/view-employee-tasks.component';

const routes: Routes = [
  {path: "dashboard", component: DashboardComponent},
  {path: "task", component: PostTaskComponent},
  {path: "task/:id/edit", component: UpdateTaskComponent},
  {path: "task-details/:id", component: ViewTaskDetailsComponent},
  {path: "employee", component: ViewEmployeeComponent},
  {path: "add-employee", component: AddEmployeeComponent},
  {path: "employee-details/:id/edit", component: ViewEmployeeDetailsComponent},
  {path: "employee-tasks/:id", component: ViewEmployeeTasksComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
