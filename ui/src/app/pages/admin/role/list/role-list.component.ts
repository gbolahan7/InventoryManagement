import {AfterContentChecked, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {RoleService} from "../role.service";
import {Router} from "@angular/router";
import {NbAccessChecker} from "@nebular/security";
import {DEFAULT_PAGE_REQUEST, GenericResponse, PageResponse} from "../../../../core/utils/template/http-util";
import {Operation, Permission, Role} from "../role.data";

@Component({
  selector: 'admin-role-list',
  styleUrls: ['./role-list.component.scss'],
  templateUrl: './role-list.component.html',
})
export class RoleListComponent  implements OnInit, AfterContentChecked{

  availablePermissions: string[] = [];
  roles: Role[] = [];
  role: Role = null;
  operation: string =  Operation.OPERATION_VIEW;
  excludeRole: string[] = ['SUPER_ADMIN', 'GUEST']

  constructor(private roleService: RoleService, private ref: ChangeDetectorRef) {

  }

  isNotExcluded(name: string) {
    return !this.excludeRole.some(role => role === name);
  }
  private fetchData(): void {
    this.roleService.getRoles(DEFAULT_PAGE_REQUEST)
      .subscribe(((p: object) => {
        const response: GenericResponse<PageResponse<Role>> = p as GenericResponse<PageResponse<Role>>;
        this.roles = response.data.content;
      }), (error) => console.log(error));
  }

  private fetchPermissions(): void {
    this.roleService.getPermissions()
      .subscribe(((p: object) => {
        const response: GenericResponse<string[]> = p as GenericResponse<string[]>;
        this.availablePermissions = response.data;
      }), (error) => console.log(error));
  }

  fetchRole(value: Role, op: string = Operation.OPERATION_VIEW): void {
    this.roleService.getRole(value.id)
      .subscribe(((p: object) => {
        const response: GenericResponse<Role> = p as GenericResponse<Role>;
        this.role = response.data;
        this.operation = op;
      }), (error) => console.log(error));
  }

  onNewClick() {
    this.operation = Operation.OPERATION_CREATE;
    this.role = new Role();
  }

  onEditClick() {
    if(this.role && this.role.id) {
      this.operation = Operation.OPERATION_MODIFY;
      this.fetchRole(this.role, this.operation);
    }
  }

  create() {
    if(this.role &&
      !!this.role.name &&
      !!this.role.description) {
      this.createRole(this.role);
    }
  }

  private createRole(payload: Role): void {
    this.roleService.createRole(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        const role = new Role();
        role.id = response.data;
        this.fetchData();
        this.fetchRole(role);
      }), (error) => console.log(error));
  }

  checkPermission(value: boolean, name: string): void {
    if(this.role.permissions && this.permissionIsValid(name)) {
      if(value) {
        this.role.permissions.push(name);
      }else {
        this.role.permissions = this.role.permissions.filter(permission => permission !== name);
      }
    }
  }

  isPressed(name: string): boolean {
    if(this.role.permissions && this.permissionIsValid(name)) {
      return this.role.permissions.some(permission => permission === name);
    }
    return false;
  }

  permissionIsValid(name: string): boolean {
    return this.availablePermissions.some(permission => permission === name);
  }


  modify(): void {
    if(this.role &&
      !!this.role.name &&
      !!this.role.description) {
      this.modifyRole(this.role);
    }

  }

  private modifyRole(payload: Role): void {
    this.roleService.editRole(payload)
      .subscribe(((p: object) => {
        const response: GenericResponse<number> = p as GenericResponse<number>;
        const role = new Role();
        role.id = response.data;
        this.fetchData();
        this.fetchRole(role);
      }), (error) => console.log(error));
  }

  ngOnInit(): void {
    this.fetchPermissions();
    this.fetchData();
  }

  ngAfterContentChecked(): void {
    this.ref.detectChanges();
  }


}
