import {DatePipe} from "@angular/common";

export class UnitAudit {
  createdDate: Date;
  modifiedDate: Date;
  createdBy: string;
  modifiedBy: string;
}
export class Unit extends UnitAudit{
  id: number;
  name: string;
  description: string;
  status: string
  items: string[];
}

export class UnitRequest extends UnitAudit{
  requestId: number;
  requestType: string;
  requestStatus: string;
  unitId: number;
  name: string;
  description: string;
  status: string
}
export class UnitHistoryAudit {
  revisionDate: Date;
  revisionId: number;
  revisionType: string;
  name: string;
  entity: Unit;
}

export const UNIT_HEADER = {
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

export const UNIT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const UNIT_DELETE = {
  deleteButtonContent: '<i class="nb-trash"></i>',
  confirmDelete: true,
}

export const UNIT_ADD = false
export const UNIT_CUSTOM = [
  {name: 'Modify', title: '<i class="nb-edit"></i>'}
]

export const UNIT_REQUEST_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const UNIT_REQUEST_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const UNIT_REQUEST_ADD = false

export const UNIT_REQUEST_HEADER = {
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

export const UNIT_AUDIT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const UNIT_AUDIT_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const UNIT_AUDIT_ADD = false

export const UNIT_AUDIT_HEADER = {
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
    title: 'Unit Name',
    type: 'string',
  },
};
