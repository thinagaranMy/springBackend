import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CUSTOM_USERComponent } from './custom-user.component';
import { CUSTOM_USERDetailComponent } from './custom-user-detail.component';
import { CUSTOM_USERPopupComponent } from './custom-user-dialog.component';
import { CUSTOM_USERDeletePopupComponent } from './custom-user-delete-dialog.component';

export const cUSTOM_USERRoute: Routes = [
    {
        path: 'custom-user',
        component: CUSTOM_USERComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTOM_USERS'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'custom-user/:id',
        component: CUSTOM_USERDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTOM_USERS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cUSTOM_USERPopupRoute: Routes = [
    {
        path: 'custom-user-new',
        component: CUSTOM_USERPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTOM_USERS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'custom-user/:id/edit',
        component: CUSTOM_USERPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTOM_USERS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'custom-user/:id/delete',
        component: CUSTOM_USERDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CUSTOM_USERS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
