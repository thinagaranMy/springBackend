import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    USER_TYPEService,
    USER_TYPEPopupService,
    USER_TYPEComponent,
    USER_TYPEDetailComponent,
    USER_TYPEDialogComponent,
    USER_TYPEPopupComponent,
    USER_TYPEDeletePopupComponent,
    USER_TYPEDeleteDialogComponent,
    uSER_TYPERoute,
    uSER_TYPEPopupRoute,
} from './';

const ENTITY_STATES = [
    ...uSER_TYPERoute,
    ...uSER_TYPEPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        USER_TYPEComponent,
        USER_TYPEDetailComponent,
        USER_TYPEDialogComponent,
        USER_TYPEDeleteDialogComponent,
        USER_TYPEPopupComponent,
        USER_TYPEDeletePopupComponent,
    ],
    entryComponents: [
        USER_TYPEComponent,
        USER_TYPEDialogComponent,
        USER_TYPEPopupComponent,
        USER_TYPEDeleteDialogComponent,
        USER_TYPEDeletePopupComponent,
    ],
    providers: [
        USER_TYPEService,
        USER_TYPEPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppUSER_TYPEModule {}
