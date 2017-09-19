import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    TRIPService,
    TRIPPopupService,
    TRIPComponent,
    TRIPDetailComponent,
    TRIPDialogComponent,
    TRIPPopupComponent,
    TRIPDeletePopupComponent,
    TRIPDeleteDialogComponent,
    tRIPRoute,
    tRIPPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tRIPRoute,
    ...tRIPPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TRIPComponent,
        TRIPDetailComponent,
        TRIPDialogComponent,
        TRIPDeleteDialogComponent,
        TRIPPopupComponent,
        TRIPDeletePopupComponent,
    ],
    entryComponents: [
        TRIPComponent,
        TRIPDialogComponent,
        TRIPPopupComponent,
        TRIPDeleteDialogComponent,
        TRIPDeletePopupComponent,
    ],
    providers: [
        TRIPService,
        TRIPPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTRIPModule {}
