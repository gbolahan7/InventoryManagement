import {DatePipe} from "@angular/common";

export class CategoryAudit {
  createdDate: Date;
  modifiedDate: Date;
  createdBy: string;
  modifiedBy: string;
}
export class Category extends CategoryAudit{
  id: number;
  name: string;
  description: string;
  status: string
  items: string[];
}

export class CategoryRequest extends CategoryAudit{
  requestId: number;
  requestType: string;
  requestStatus: string;
  categoryId: number;
  name: string;
  description: string;
  status: string
  items: string[];
}
export class CategoryHistoryAudit {
  revisionDate: Date;
  revisionId: number;
  revisionType: string;
  name: string;
  entity: Category;
}

export const CATEGORY_HEADER = {
  id: {
    title: 'ID',
    type: 'number',
  },
  name: {
    title: 'Name',
    type: 'string',
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

export const CATEGORY_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const CATEGORY_DELETE = {
  deleteButtonContent: '<i class="nb-trash"></i>',
  confirmDelete: true,
}

export const CATEGORY_ADD = false
export const CATEGORY_CUSTOM = [
  {name: 'Modify', title: '<i class="nb-edit"></i>'}
]

export const CATEGORY_REQUEST_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const CATEGORY_REQUEST_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const CATEGORY_REQUEST_ADD = false

export const CATEGORY_REQUEST_HEADER = {
  requestId: {
    title: 'ID',
    type: 'number',
  },
  name: {
    title: 'Name',
    type: 'string',
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

export const CATEGORY_AUDIT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const CATEGORY_AUDIT_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const CATEGORY_AUDIT_ADD = false

export const CATEGORY_AUDIT_HEADER = {
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
    title: 'Category Name',
    type: 'string',
  },
};
