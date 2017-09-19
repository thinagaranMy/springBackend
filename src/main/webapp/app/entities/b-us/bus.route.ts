import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BUSComponent } from './bus.component';
import { BUSDetailComponent } from './bus-detail.component';
import { BUSPopupComponent } from './bus-dialog.component';
import { BUSDeletePopupComponent } from './bus-delete-dialog.component';

export const bUSRoute: Routes = [
    {
        path: 'bus',
        component: BUSComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BUSES'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bus/:id',
        component: BUSDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BUSES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bUSPopupRoute: Routes = [
    {
        path: 'bus-new',
        component: BUSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BUSES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bus/:id/edit',
        component: BUSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BUSES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bus/:id/delete',
        component: BUSDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BUSES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
