import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'truncate'})
export class TruncateDotPipe implements PipeTransform {
  size: number = 140;

  transform(input: string): string {
    return input.length > this.size ? input.slice(0, this.size - 1) + "…" : input;
  }

}
