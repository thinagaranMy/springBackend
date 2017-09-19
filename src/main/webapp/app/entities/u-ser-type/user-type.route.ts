import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { USER_TYPEComponent } from './user-type.component';
import { USER_TYPEDetailComponent } from './user-type-detail.component';
import { USER_TYPEPopupComponent } from './user-type-dialog.component';
import { USER_TYPEDeletePopupComponent } from './user-type-delete-dialog.component';

export const uSER_TYPERoute: Routes = [
    {
        path: 'user-type',
        component: USER_TYPEComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'USER_TYPES'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-type/:id',
        component: USER_TYPEDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'USER_TYPES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const uSER_TYPEPopupRoute: Routes = [
    {
        path: 'user-type-new',
        component: USER_TYPEPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'USER_TYPES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-type/:id/edit',
        component: USER_TYPEPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'USER_TYPES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-type/:id/delete',
        component: USER_TYPEDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'USER_TYPES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
