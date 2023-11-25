export class Role {
  id: number;
  name: string;
  description: string;
  permissions: string[];
}

export class Operation {
  public static OPERATION_VIEW: string = 'view';
  public static OPERATION_CREATE: string = 'create';
  public static OPERATION_MODIFY: string = 'modify';
}

export class Permission {
  name: string;
  sub: PermissionSub[];
}

export class PermissionSub {
  name: string;
  item: PermissionItem[];
}

export class PermissionItem {
  name: string;
}
