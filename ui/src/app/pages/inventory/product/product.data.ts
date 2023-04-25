import {DatePipe} from "@angular/common";

export class ProductAudit {
  createdDate: Date;
  modifiedDate: Date;
  createdBy: string;
  modifiedBy: string;
}
export class Product extends ProductAudit{
  id: number;
  name: string;
  category: string;
  status: string
  brand: string
  code: string
  unit: string
  unitPrice: number
  discount: number
  manufacturedDate: Date
  expiryDate: Date
  warehouse: string
  warehousePrice: number
  taxInPercentage: number
  quantity: number
  pictureUrl: string
  picturePayload: string;
}
export class PictureSnippet {
  pending: boolean = false;
  status: string = 'init';
  constructor(public src: string, public file: File) {}
}
export class ProductRequest extends ProductAudit{
  requestId: number;
  requestType: string;
  requestStatus: string;
  productId: number;
  name: string;
  category: string;
  status: string
  brand: string
  code: string
  unit: string
  unitPrice: number
  discount: number
  manufacturedDate: Date
  expiryDate: Date
  warehouse: string
  warehousePrice: number
  taxInPercentage: number
  quantity: number
  picturePayload: string;
  pictureUrl: string;
}
export class ProductHistoryAudit {
  revisionDate: Date;
  revisionId: number;
  revisionType: string;
  name: string;
  entity: Product;
}

export const PRODUCT_HEADER = {
  id: {
    title: 'ID',
    type: 'number',
  },
  name: {
    title: 'Name',
    type: 'string',
  },
  category: {
    title: 'Category',
    type: 'string',
  },
  unit: {
    title: 'Unit',
    type: 'string',
  },
  quantity: {
    title: 'Quantity',
    type: 'number',
  },
  status: {
    title: 'Status',
    type: 'string',
  }
};

export const PRODUCT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PRODUCT_DELETE = {
  deleteButtonContent: '<i class="nb-trash"></i>',
  confirmDelete: true,
}

export const PRODUCT_ADD = false
export const PRODUCT_CUSTOM = [
  {name: 'Modify', title: '<i class="nb-edit"></i>'}
]

export const PRODUCT_REQUEST_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PRODUCT_REQUEST_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const PRODUCT_REQUEST_ADD = false

export const PRODUCT_REQUEST_HEADER = {
  requestId: {
    title: 'ID',
    type: 'number',
  },
  name: {
    title: 'Name',
    type: 'string',
  },
  category: {
    title: 'Category',
    type: 'string',
  },
  unit: {
    title: 'Unit',
    type: 'string',
  },
  quantity: {
    title: 'Quantity',
    type: 'number',
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

export const PRODUCT_AUDIT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PRODUCT_AUDIT_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const PRODUCT_AUDIT_ADD = false

export const PRODUCT_AUDIT_HEADER = {
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
  name: {
    title: 'Product Name',
    type: 'string',
  },
};
