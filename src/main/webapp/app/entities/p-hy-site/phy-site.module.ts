import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    PHY_SITEService,
    PHY_SITEPopupService,
    PHY_SITEComponent,
    PHY_SITEDetailComponent,
    PHY_SITEDialogComponent,
    PHY_SITEPopupComponent,
    PHY_SITEDeletePopupComponent,
    PHY_SITEDeleteDialogComponent,
    pHY_SITERoute,
    pHY_SITEPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pHY_SITERoute,
    ...pHY_SITEPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PHY_SITEComponent,
        PHY_SITEDetailComponent,
        PHY_SITEDialogComponent,
        PHY_SITEDeleteDialogComponent,
        PHY_SITEPopupComponent,
        PHY_SITEDeletePopupComponent,
    ],
    entryComponents: [
        PHY_SITEComponent,
        PHY_SITEDialogComponent,
        PHY_SITEPopupComponent,
        PHY_SITEDeleteDialogComponent,
        PHY_SITEDeletePopupComponent,
    ],
    providers: [
        PHY_SITEService,
        PHY_SITEPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppPHY_SITEModule {}
