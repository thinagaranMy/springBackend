import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TRIPComponent } from './trip.component';
import { TRIPDetailComponent } from './trip-detail.component';
import { TRIPPopupComponent } from './trip-dialog.component';
import { TRIPDeletePopupComponent } from './trip-delete-dialog.component';

export const tRIPRoute: Routes = [
    {
        path: 'trip',
        component: TRIPComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TRIPS'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'trip/:id',
        component: TRIPDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TRIPS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tRIPPopupRoute: Routes = [
    {
        path: 'trip-new',
        component: TRIPPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TRIPS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'trip/:id/edit',
        component: TRIPPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TRIPS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'trip/:id/delete',
        component: TRIPDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TRIPS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
