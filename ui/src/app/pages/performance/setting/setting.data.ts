import {DatePipe} from "@angular/common";

export class PerformanceSettingAudit {
  createdDate: Date;
  modifiedDate: Date;
  createdBy: string;
  modifiedBy: string;
}

export class PerformanceSetting extends PerformanceSettingAudit {
  id: number;
  name: string;
  staffPeriod: string;
  staffStartTime: Date;
  staffStopTime: Date;
  staffUpdateType: string;
  staffBonusPoint: number;
}

export class StaffPerformance extends PerformanceSettingAudit {
  id: number;
  username: string;
  period: string;
  isNew: boolean
  type: string;
  averagePeriodicPerformance: number;
  bonusPoint: number;
}

export class PerformanceSettingRequest extends PerformanceSettingAudit {
  requestId: number;
  requestType: string;
  requestStatus: string;
  performanceSettingId: number;
  name: string;
  staffPeriod: string;
  staffStartTime: Date;
  staffStopTime: Date;
  staffUpdateType: string;
  staffBonusPoint: number;
}

export class PerformanceSettingHistoryAudit {
  revisionDate: Date;
  revisionId: number;
  revisionType: string;
  name: string;
  entity: PerformanceSetting;
}

export const PERFORMANCE_STAFF_HEADER = {
  id: {
    title: 'ID',
    type: 'number',
    filter: true,
  },
  username: {
    title: 'User',
    type: 'string',
    filter: true,
  },
  period: {
    title: 'Period',
    type: 'string',
    filter: true,
  },
  isNew: {
    title: 'New',
    type: 'boolean',
    filter: true,
  },
  type: {
    title: 'Type',
    type: 'string',
    filter: true,
  },
  averagePeriodicPerformance: {
    title: 'Measure (%)',
    type: 'number',
    filter: true,
  },
  bonusPoint: {
    title: 'Bonus Point',
    type: 'number',
    filter: true,
  },

};

export const PerformanceSetting_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PerformanceSetting_DELETE = {
  deleteButtonContent: '<i class="nb-trash"></i>',
  confirmDelete: true,
}

export const PerformanceSetting_ADD = false
export const PerformanceSetting_CUSTOM = [
  {name: 'Modify', title: '<i class="nb-edit"></i>'}
]

export const PerformanceSetting_REQUEST_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PerformanceSetting_REQUEST_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const PerformanceSetting_REQUEST_ADD = false

export const PerformanceSetting_REQUEST_HEADER = {
  requestId: {
    title: 'ID',
    type: 'number',
  },
  name: {
    title: 'Name',
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

export const PerformanceSetting_AUDIT_MODIFY = {
  editButtonContent: '',
  saveButtonContent: '',
  cancelButtonContent: '',
};

export const PerformanceSetting_AUDIT_DELETE = {
  deleteButtonContent: '',
  confirmDelete: false,
}

export const PerformanceSetting_AUDIT_ADD = false

export const PerformanceSetting_AUDIT_HEADER = {
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
    title: 'PerformanceSetting Name',
    type: 'string',
  },
};
