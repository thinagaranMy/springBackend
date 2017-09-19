import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    COUNTRYService,
    COUNTRYPopupService,
    COUNTRYComponent,
    COUNTRYDetailComponent,
    COUNTRYDialogComponent,
    COUNTRYPopupComponent,
    COUNTRYDeletePopupComponent,
    COUNTRYDeleteDialogComponent,
    cOUNTRYRoute,
    cOUNTRYPopupRoute,
} from './';

const ENTITY_STATES = [
    ...cOUNTRYRoute,
    ...cOUNTRYPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        COUNTRYComponent,
        COUNTRYDetailComponent,
        COUNTRYDialogComponent,
        COUNTRYDeleteDialogComponent,
        COUNTRYPopupComponent,
        COUNTRYDeletePopupComponent,
    ],
    entryComponents: [
        COUNTRYComponent,
        COUNTRYDialogComponent,
        COUNTRYPopupComponent,
        COUNTRYDeleteDialogComponent,
        COUNTRYDeletePopupComponent,
    ],
    providers: [
        COUNTRYService,
        COUNTRYPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCOUNTRYModule {}
