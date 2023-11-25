import { NgModule } from '@angular/core';
import { NbButtonModule, NbCardModule } from '@nebular/theme';

import { ThemeModule } from '../../theme/theme.module';
import { MiscellaneousRoutingModule } from './miscellaneous-routing.module';
import { MiscellaneousComponent } from './miscellaneous.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {NbSecurityModule} from "@nebular/security";
import {NoAccessComponent} from "./no-access/no-access.component";

@NgModule({
  imports: [
    ThemeModule,
    NbCardModule,
    NbButtonModule,
    MiscellaneousRoutingModule,
    NbSecurityModule,
  ],
  declarations: [
    MiscellaneousComponent,
    NotFoundComponent,
    NoAccessComponent
  ],
})
export class MiscellaneousModule { }
