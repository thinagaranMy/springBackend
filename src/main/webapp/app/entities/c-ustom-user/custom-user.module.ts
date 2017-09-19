import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    CUSTOM_USERService,
    CUSTOM_USERPopupService,
    CUSTOM_USERComponent,
    CUSTOM_USERDetailComponent,
    CUSTOM_USERDialogComponent,
    CUSTOM_USERPopupComponent,
    CUSTOM_USERDeletePopupComponent,
    CUSTOM_USERDeleteDialogComponent,
    cUSTOM_USERRoute,
    cUSTOM_USERPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cUSTOM_USERRoute,
    ...cUSTOM_USERPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CUSTOM_USERComponent,
        CUSTOM_USERDetailComponent,
        CUSTOM_USERDialogComponent,
        CUSTOM_USERDeleteDialogComponent,
        CUSTOM_USERPopupComponent,
        CUSTOM_USERDeletePopupComponent,
    ],
    entryComponents: [
        CUSTOM_USERComponent,
        CUSTOM_USERDialogComponent,
        CUSTOM_USERPopupComponent,
        CUSTOM_USERDeleteDialogComponent,
        CUSTOM_USERDeletePopupComponent,
    ],
    providers: [
        CUSTOM_USERService,
        CUSTOM_USERPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCUSTOM_USERModule {}
