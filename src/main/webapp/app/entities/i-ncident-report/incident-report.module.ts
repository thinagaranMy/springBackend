import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    INCIDENT_REPORTService,
    INCIDENT_REPORTPopupService,
    INCIDENT_REPORTComponent,
    INCIDENT_REPORTDetailComponent,
    INCIDENT_REPORTDialogComponent,
    INCIDENT_REPORTPopupComponent,
    INCIDENT_REPORTDeletePopupComponent,
    INCIDENT_REPORTDeleteDialogComponent,
    iNCIDENT_REPORTRoute,
    iNCIDENT_REPORTPopupRoute,
} from './';

const ENTITY_STATES = [
    ...iNCIDENT_REPORTRoute,
    ...iNCIDENT_REPORTPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        INCIDENT_REPORTComponent,
        INCIDENT_REPORTDetailComponent,
        INCIDENT_REPORTDialogComponent,
        INCIDENT_REPORTDeleteDialogComponent,
        INCIDENT_REPORTPopupComponent,
        INCIDENT_REPORTDeletePopupComponent,
    ],
    entryComponents: [
        INCIDENT_REPORTComponent,
        INCIDENT_REPORTDialogComponent,
        INCIDENT_REPORTPopupComponent,
        INCIDENT_REPORTDeleteDialogComponent,
        INCIDENT_REPORTDeletePopupComponent,
    ],
    providers: [
        INCIDENT_REPORTService,
        INCIDENT_REPORTPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppINCIDENT_REPORTModule {}
