import {Role} from "../role/role.data";

export class User {
  id: number;
  username: string;
  email: string;
  roles: Role[];
  role: Role;
  status: string;
}

export class Operation {
  public static OPERATION_VIEW: string = 'view';
  public static OPERATION_MODIFY: string = 'modify';
}

