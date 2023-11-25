import {DatePipe} from "@angular/common";

export class PurchaseOrderAudit {
  createdDate: Date;
  modifiedDate: Date;
  createdBy: string;
  modifiedBy: string;
}
export class PurchaseOrderItem {
  id: number;
  productName: string;
  productCode: string;
  amount: number;
  quantity: number;
  vatEnabled: boolean;
  totalAmount: number
  purchaseOrderId: string;
}
export class PurchaseOrder extends PurchaseOrderAudit{
  id: number;
  description: string;
  purchasedDate: Date;
  status: string
  items: PurchaseOrderItem[];
}

export class PurchaseOrderRequest extends PurchaseOrderAudit{
  requestId: number;
  requestType: string;
  requestStatus: string;
  purchaseOrderId: number;
  description: string;
  status: string;
  purchasedDate: Date;
}
export class PurchaseOrderHistoryAudit {
  revisionDate: Date;
  revisionId: number;
  revisionType: string;
  name: string;
  entity: PurchaseOrder;
}

export const PurchaseOrder_HEADER = {
  id: {
    title: 'ID',
    type: 'number',
  },
  description: {
    title: 'Description',
    type: 'string',
  },
  status: {
    title: 'Status',
    type: 'string',
  }
};
export const PurchaseOrderItem_HEADER = {
  id: {
    title: 'ID',
    type: 'number',
  },
  productName: {
    title: 'Product Name',
    type: 'string',
  },
  productCode: {
    title: 'Product Code',
    type: 'string',
  },
  quantity: {
    title: 'Quantity',
    type: 'number',
  },
  amount: {
    title: 'Unit Price',
    type: 'number',
  }
};

export const PurchaseOrder_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PurchaseOrder_DELETE = {
  deleteButtonContent: '<i class="nb-trash"></i>',
  confirmDelete: true,
}

export const PurchaseOrder_ADD = false
export const PurchaseOrder_CUSTOM = [
  {name: 'Modify', title: '<i class="nb-edit"></i>'}
]

export const PurchaseOrder_REQUEST_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PurchaseOrder_REQUEST_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const PurchaseOrder_REQUEST_ADD = false

export const PurchaseOrder_REQUEST_HEADER = {
  requestId: {
    title: 'ID',
    type: 'number',
  },
  description: {
    title: 'Description',
    type: 'string',
  },
  status: {
    title: 'Status',
    type: 'string',
  },
  requestType: {
    title: 'Type',
    type: 'string',
  },
  createdBy: {
    title: 'Created By',
    type: 'string',
  },
  requestStatus: {
    title: 'Request Status',
    type: 'string',
  },
};

export const PurchaseOrder_AUDIT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PurchaseOrder_AUDIT_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const PurchaseOrder_AUDIT_ADD = false

export const PurchaseOrder_AUDIT_HEADER = {
  revisionId: {
    title: 'Revision Id',
    type: 'number',
  },
  revisionType: {
    title: 'Revision Type',
    type: 'string',
  },
  revisionDate: {
    title: 'Revision Date',
    type: 'Date',
    valuePrepareFunction: (revisionDate) => {
      return new DatePipe('en').transform(new Date(revisionDate), 'dd MMM yyyy');
    }
  },
  purchasedDate: {
    title: 'Purchased Date',
    type: 'Date',
    valuePrepareFunction: (purchasedDate) => {
      return new DatePipe('en').transform(new Date(purchasedDate), 'dd MMM yyyy');
    }
  },
};


export const PURCHASE_ORDER_ITEM_HEADER = {
};

export const PURCHASE_ORDER_ITEM_MODIFY = {
  editButtonContent: '<i class="nb-edit"></i>',
  saveButtonContent: '<i class="nb-checkmark"></i>',
  cancelButtonContent: '<i class="nb-close"></i>',
}

export const PURCHASE_ORDER_ITEM_DELETE = {
  deleteButtonContent: '<i class="nb-trash"></i>',
  confirmDelete: true,
}

export const PURCHASE_ORDER_ITEM_ADD = {
  addButtonContent: '<i class="nb-plus"></i>',
  createButtonContent: '<i class="nb-checkmark"></i>',
  cancelButtonContent: '<i class="nb-close"></i>',
  confirmCreate: true,
}
