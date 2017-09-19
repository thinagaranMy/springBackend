import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    STATIONService,
    STATIONPopupService,
    STATIONComponent,
    STATIONDetailComponent,
    STATIONDialogComponent,
    STATIONPopupComponent,
    STATIONDeletePopupComponent,
    STATIONDeleteDialogComponent,
    sTATIONRoute,
    sTATIONPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sTATIONRoute,
    ...sTATIONPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        STATIONComponent,
        STATIONDetailComponent,
        STATIONDialogComponent,
        STATIONDeleteDialogComponent,
        STATIONPopupComponent,
        STATIONDeletePopupComponent,
    ],
    entryComponents: [
        STATIONComponent,
        STATIONDialogComponent,
        STATIONPopupComponent,
        STATIONDeleteDialogComponent,
        STATIONDeletePopupComponent,
    ],
    providers: [
        STATIONService,
        STATIONPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSTATIONModule {}
