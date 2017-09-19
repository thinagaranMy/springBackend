import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppBUSModule } from './b-us/bus.module';
import { AppCOUNTRYModule } from './c-ountry/country.module';
import { AppCUSTOM_USERModule } from './c-ustom-user/custom-user.module';
import { AppINCIDENT_REPORTModule } from './i-ncident-report/incident-report.module';
import { AppPHY_SITEModule } from './p-hy-site/phy-site.module';
import { AppROUTESModule } from './r-outes/routes.module';
import { AppSTATEModule } from './s-tate/state.module';
import { AppSTATIONModule } from './s-tation/station.module';
import { AppTRIPModule } from './t-rip/trip.module';
import { AppUSER_TYPEModule } from './u-ser-type/user-type.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AppBUSModule,
        AppCOUNTRYModule,
        AppCUSTOM_USERModule,
        AppINCIDENT_REPORTModule,
        AppPHY_SITEModule,
        AppROUTESModule,
        AppSTATEModule,
        AppSTATIONModule,
        AppTRIPModule,
        AppUSER_TYPEModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEntityModule {}
