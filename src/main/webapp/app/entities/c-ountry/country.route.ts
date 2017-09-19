import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { COUNTRYComponent } from './country.component';
import { COUNTRYDetailComponent } from './country-detail.component';
import { COUNTRYPopupComponent } from './country-dialog.component';
import { COUNTRYDeletePopupComponent } from './country-delete-dialog.component';

export const cOUNTRYRoute: Routes = [
    {
        path: 'country',
        component: COUNTRYComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'COUNTRIES'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'country/:id',
        component: COUNTRYDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'COUNTRIES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cOUNTRYPopupRoute: Routes = [
    {
        path: 'country-new',
        component: COUNTRYPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'COUNTRIES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country/:id/edit',
        component: COUNTRYPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'COUNTRIES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country/:id/delete',
        component: COUNTRYDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'COUNTRIES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
