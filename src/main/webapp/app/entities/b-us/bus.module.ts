import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    BUSService,
    BUSPopupService,
    BUSComponent,
    BUSDetailComponent,
    BUSDialogComponent,
    BUSPopupComponent,
    BUSDeletePopupComponent,
    BUSDeleteDialogComponent,
    bUSRoute,
    bUSPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bUSRoute,
    ...bUSPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BUSComponent,
        BUSDetailComponent,
        BUSDialogComponent,
        BUSDeleteDialogComponent,
        BUSPopupComponent,
        BUSDeletePopupComponent,
    ],
    entryComponents: [
        BUSComponent,
        BUSDialogComponent,
        BUSPopupComponent,
        BUSDeleteDialogComponent,
        BUSDeletePopupComponent,
    ],
    providers: [
        BUSService,
        BUSPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppBUSModule {}
