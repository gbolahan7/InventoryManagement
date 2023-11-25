import { NbMenuService } from '@nebular/theme';
import { Component } from '@angular/core';

@Component({
  selector: 'ngx-no-access',
  styleUrls: ['./no-access.component.scss'],
  templateUrl: './no-access.component.html',
})
export class NoAccessComponent {

  constructor(private menuService: NbMenuService) {
  }

}
