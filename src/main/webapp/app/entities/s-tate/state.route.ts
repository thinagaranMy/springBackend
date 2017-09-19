import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { STATEComponent } from './state.component';
import { STATEDetailComponent } from './state-detail.component';
import { STATEPopupComponent } from './state-dialog.component';
import { STATEDeletePopupComponent } from './state-delete-dialog.component';

export const sTATERoute: Routes = [
    {
        path: 'state',
        component: STATEComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATES'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'state/:id',
        component: STATEDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sTATEPopupRoute: Routes = [
    {
        path: 'state-new',
        component: STATEPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state/:id/edit',
        component: STATEPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state/:id/delete',
        component: STATEDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'STATES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
