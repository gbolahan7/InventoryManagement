import { HttpParameterCodec } from '@angular/common/http';

export class GenericResponse<T> {
  data: T;
  status: string;
  message: string;
}

export class PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  "number": number;
  "numberOfElements": number;
}
export interface PageRequest {
  page?: number;
  size?: number;
  sortDirection?: string;
  sortBy?: string;
}

export const DEFAULT_PAGE_REQUEST = new class PageRequest {
  page = 0;
  size = 100;
  sortBy = 'participant';
  sortDirection = 'asc';
};

export class CustomHttpParameterCodec implements HttpParameterCodec {
  encodeKey(k: string): string {
    return encodeURIComponent(k);
  }
  encodeValue(v: string): string {
    return encodeURIComponent(v);
  }
  decodeKey(k: string): string {
    return decodeURIComponent(k);
  }
  decodeValue(v: string): string {
    return decodeURIComponent(v);
  }
}
