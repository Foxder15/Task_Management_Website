import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

const BASIC_URL = "http://localhost:8080/";


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any> {
    return this.http.get(BASIC_URL + "api/admin/users",{
      headers:this.createAuthorizationHeader()
    })
  }

  postTask(taskDto: any): Observable<any> {
    return this.http.post(BASIC_URL + "api/admin/tasks", taskDto, {
      headers:this.createAuthorizationHeader()
    })
  }

  postUser(userDto: any): Observable<any> {
    return this.http.post(BASIC_URL + "api/admin/users", userDto, {
      headers:this.createAuthorizationHeader()
    })
  }

  getTasks(): Observable<any> {
    return this.http.get(BASIC_URL + "api/admin/tasks",{
      headers:this.createAuthorizationHeader()
    })
  }

  getTasksByUserId(user_id: number): Observable<any> {
    return this.http.get(BASIC_URL + "api/admin/users/tasks/" + user_id, {
      headers:this.createAuthorizationHeader()
    })
  }

  deleteTask(task_id: number): Observable<any> {
    return this.http.delete(BASIC_URL + "api/admin/tasks/" + task_id, {
      headers:this.createAuthorizationHeader()
    })
  }


  deleteUser(user_id: number): Observable<any> {
    return this.http.delete(BASIC_URL + "api/admin/users/" + user_id, {
      headers:this.createAuthorizationHeader()
    })
  }

  updateTask(id: number, taskDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `api/admin/tasks/${id}`, taskDto, {
      headers:this.createAuthorizationHeader()
    })
  }

  updateUser(id: number, userDto: any): Observable<any> {
    return this.http.put(BASIC_URL + `api/admin/users/${id}`, userDto, {
      headers:this.createAuthorizationHeader()
    })
  }

  searchTask(title: string): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/tasks/search/${title}`, {
      headers:this.createAuthorizationHeader()
    })
  }

  getTaskById(task_id: number): Observable<any> {
    return this.http.get(BASIC_URL + "api/admin/tasks/" + task_id, {
      headers:this.createAuthorizationHeader()
    })
  }

  getUserById(user_id: number): Observable<any> {
    return this.http.get(BASIC_URL + "api/admin/users/" + user_id, {
      headers:this.createAuthorizationHeader()
    })
  }




  createComment(task_id: number, content: string): Observable<any> {
    const params = {
      content: content
    }
    return this.http.post(BASIC_URL + "api/admin/tasks/comment/" + task_id, null, {
      params: params,
      headers:this.createAuthorizationHeader()
    })
  }

  getCommentByTask(task_id: number): Observable<any> {
    return this.http.get(BASIC_URL + "api/admin/comments/" + task_id, {
      headers:this.createAuthorizationHeader()
    })
  }

  private createAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders().set(
      'Authorization','Bearer ' + StorageService.getToken()
    )
  }
}
