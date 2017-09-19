import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { INCIDENT_REPORTComponent } from './incident-report.component';
import { INCIDENT_REPORTDetailComponent } from './incident-report-detail.component';
import { INCIDENT_REPORTPopupComponent } from './incident-report-dialog.component';
import { INCIDENT_REPORTDeletePopupComponent } from './incident-report-delete-dialog.component';

export const iNCIDENT_REPORTRoute: Routes = [
    {
        path: 'incident-report',
        component: INCIDENT_REPORTComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'INCIDENT_REPORTS'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'incident-report/:id',
        component: INCIDENT_REPORTDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'INCIDENT_REPORTS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iNCIDENT_REPORTPopupRoute: Routes = [
    {
        path: 'incident-report-new',
        component: INCIDENT_REPORTPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'INCIDENT_REPORTS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'incident-report/:id/edit',
        component: INCIDENT_REPORTPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'INCIDENT_REPORTS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'incident-report/:id/delete',
        component: INCIDENT_REPORTDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'INCIDENT_REPORTS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
