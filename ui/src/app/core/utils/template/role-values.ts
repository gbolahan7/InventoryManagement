import {NbAccessControl} from "@nebular/security/security.options";

export const ROLE_ACCESS_CONTROL: NbAccessControl = {
  inventory_product_add: {
    product: ['add'],
  },
  inventory_product_view: {
    product: ['view'],
  },
  inventory_category_view: {
    category: ['view'],
  },
  inventory_category_create: {
    category: ['create'],
  },
  inventory_category_modify: {
    category: ['modify'],
  },
  inventory_category_access: {
    category: ['access'],
  },
  admin_role_operation: {
    role: ['operation'],
  },
  admin_user_operation: {
    user: ['operation'],
  },
inventory_unit_view: {
    unit: ['view'],
  },
  inventory_unit_create: {
    unit: ['create'],
  },
  inventory_unit_modify: {
    unit: ['modify'],
  },
  inventory_unit_access: {
    unit: ['access'],
  },
}
