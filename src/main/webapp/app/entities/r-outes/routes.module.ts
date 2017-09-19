import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    ROUTESService,
    ROUTESPopupService,
    ROUTESComponent,
    ROUTESDetailComponent,
    ROUTESDialogComponent,
    ROUTESPopupComponent,
    ROUTESDeletePopupComponent,
    ROUTESDeleteDialogComponent,
    rOUTESRoute,
    rOUTESPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rOUTESRoute,
    ...rOUTESPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ROUTESComponent,
        ROUTESDetailComponent,
        ROUTESDialogComponent,
        ROUTESDeleteDialogComponent,
        ROUTESPopupComponent,
        ROUTESDeletePopupComponent,
    ],
    entryComponents: [
        ROUTESComponent,
        ROUTESDialogComponent,
        ROUTESPopupComponent,
        ROUTESDeleteDialogComponent,
        ROUTESDeletePopupComponent,
    ],
    providers: [
        ROUTESService,
        ROUTESPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppROUTESModule {}
