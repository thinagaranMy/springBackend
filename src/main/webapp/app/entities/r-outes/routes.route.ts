import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ROUTESComponent } from './routes.component';
import { ROUTESDetailComponent } from './routes-detail.component';
import { ROUTESPopupComponent } from './routes-dialog.component';
import { ROUTESDeletePopupComponent } from './routes-delete-dialog.component';

export const rOUTESRoute: Routes = [
    {
        path: 'routes',
        component: ROUTESComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ROUTES'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'routes/:id',
        component: ROUTESDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ROUTES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rOUTESPopupRoute: Routes = [
    {
        path: 'routes-new',
        component: ROUTESPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ROUTES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'routes/:id/edit',
        component: ROUTESPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ROUTES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'routes/:id/delete',
        component: ROUTESDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ROUTES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
