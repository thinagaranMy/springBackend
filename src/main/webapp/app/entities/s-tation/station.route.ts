import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { STATIONComponent } from './station.component';
import { STATIONDetailComponent } from './station-detail.component';
import { STATIONPopupComponent } from './station-dialog.component';
import { STATIONDeletePopupComponent } from './station-delete-dialog.component';

export const sTATIONRoute: Routes = [
    {
        path: 'station',
        component: STATIONComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATIONS'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'station/:id',
        component: STATIONDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATIONS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sTATIONPopupRoute: Routes = [
    {
        path: 'station-new',
        component: STATIONPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATIONS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'station/:id/edit',
        component: STATIONPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATIONS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'station/:id/delete',
        component: STATIONDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATIONS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
