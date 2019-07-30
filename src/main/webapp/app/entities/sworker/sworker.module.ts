import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BugtrackerSharedModule } from 'app/shared';
import {
  SworkerComponent,
  SworkerDetailComponent,
  SworkerUpdateComponent,
  SworkerDeletePopupComponent,
  SworkerDeleteDialogComponent,
  sworkerRoute,
  sworkerPopupRoute
} from './';

const ENTITY_STATES = [...sworkerRoute, ...sworkerPopupRoute];

@NgModule({
  imports: [BugtrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SworkerComponent,
    SworkerDetailComponent,
    SworkerUpdateComponent,
    SworkerDeleteDialogComponent,
    SworkerDeletePopupComponent
  ],
  entryComponents: [SworkerComponent, SworkerUpdateComponent, SworkerDeleteDialogComponent, SworkerDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BugtrackerSworkerModule {}
