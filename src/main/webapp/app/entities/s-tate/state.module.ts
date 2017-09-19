import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    STATEService,
    STATEPopupService,
    STATEComponent,
    STATEDetailComponent,
    STATEDialogComponent,
    STATEPopupComponent,
    STATEDeletePopupComponent,
    STATEDeleteDialogComponent,
    sTATERoute,
    sTATEPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sTATERoute,
    ...sTATEPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        STATEComponent,
        STATEDetailComponent,
        STATEDialogComponent,
        STATEDeleteDialogComponent,
        STATEPopupComponent,
        STATEDeletePopupComponent,
    ],
    entryComponents: [
        STATEComponent,
        STATEDialogComponent,
        STATEPopupComponent,
        STATEDeleteDialogComponent,
        STATEDeletePopupComponent,
    ],
    providers: [
        STATEService,
        STATEPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSTATEModule {}
