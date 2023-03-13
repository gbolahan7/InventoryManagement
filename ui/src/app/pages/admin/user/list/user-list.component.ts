import {AfterContentChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Operation, User} from "../user.data";
import {RoleService} from "../../role/role.service";
import {Role} from "../../role/role.data";

@Component({
  selector: 'admin-user-list',
  styleUrls: ['./user-list.component.scss'],
  templateUrl: './user-list.component.html',
})
export class UserListComponent implements OnInit, AfterContentChecked{

  roles: Role[] = [];
  users: User[] = [];
  user: User = null;
  operation: string =  Operation.OPERATION_VIEW;
  statuses: {key: string, value: string}[] = [{key: 'ACTIVE', value: 'Active'}, {key: 'INACTIVE', value: 'Inactive'}]

  constructor(private userService: UserService,private roleService: RoleService, private ref: ChangeDetectorRef) {

  }

  private fetchRoles(): void {
    this.roleService.getRoles(DEFAULT_PAGE_REQUEST)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<Role>> = p as GenericResponse<PageResponse<Role>>;
        this.roles = response.data.content;
      }), (error) => console.log(error));
  }

  private fetchData(): void {
    this.userService.getUsers(DEFAULT_PAGE_REQUEST)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<User>> = p as GenericResponse<PageResponse<User>>;
        this.users = response.data.content.map(user => {
          user.role = (user && user.roles && user.roles.length === 1) ? user.roles[0] : null;
          return user;
        });
      }), (error) => console.log(error));
  }

  fetchUser(value: User, op: string = Operation.OPERATION_VIEW): void {
    this.userService.getUser(value.id)
      .subscribe(((p: object) => {
        const response: GenericResponse<User> = p as GenericResponse<User>;
        this.user = response.data;
        this.user.role = (this.user && this.user.roles && this.user.roles.length === 1) ? this.user.roles[0] : null;
        this.operation = op;
      }), (error) => console.log(error));
  }

  onEditClick() {
    if(this.user && this.user.id) {
      this.operation = Operation.OPERATION_MODIFY;
      this.fetchUser(this.user, this.operation);
    }
  }


  modify(): void {
    if(this.user &&
      !!this.user.status &&
      !!this.user.role) {
      let newRole = new Role();
      newRole.id = this.user.role.id;
      this.user.roles = [newRole];
      this.modifyRole(this.user);
    }

  }

  private modifyRole(payload: User): void {
    this.userService.modifyUser(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        const user1 = new User();
        user1.id = response.data;
        this.fetchData();
        this.fetchUser(user1);
      }), (error) => console.log(error));
  }

  ngOnInit(): void {
    this.fetchRoles();
    this.fetchData();
  }

  ngAfterContentChecked(): void {
    this.ref.detectChanges();
  }


}
