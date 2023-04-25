import {NbAccessControl} from "@nebular/security/security.options";

export const ROLE_ACCESS_CONTROL: NbAccessControl = {
  inventory_product_view: {
    product: ['view'],
  },
  inventory_product_create: {
    product: ['create'],
  },
  inventory_product_modify: {
    product: ['modify'],
  },
  inventory_product_access: {
    product: ['access'],
  },
  inventory_product_delete: {
    product: ['delete'],
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
  inventory_category_delete: {
    category: ['delete'],
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
  inventory_unit_delete: {
    unit: ['delete'],
  },
  inventory_purchase_order_view: {
    purchase_order: ['view'],
  },
  inventory_purchase_order_create: {
    purchase_order: ['create'],
  },
  inventory_purchase_order_modify: {
    purchase_order: ['modify'],
  },
  inventory_purchase_order_access: {
    purchase_order: ['access'],
  },
  inventory_purchase_order_delete: {
    purchase_order: ['delete'],
  },
  dashboard_chart_view: {
    chart: ['view'],
  },
  report_list_view: {
    report: ['view'],
  },
  performance_setting_view: {
    setting: ['view'],
  },
  performance_setting_modify: {
    setting: ['modify'],
  },
  performance_setting_access: {
    setting: ['access'],
  },
  performance_staff_view: {
    staff: ['view'],
  },
}
