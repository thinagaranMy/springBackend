import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PHY_SITEComponent } from './phy-site.component';
import { PHY_SITEDetailComponent } from './phy-site-detail.component';
import { PHY_SITEPopupComponent } from './phy-site-dialog.component';
import { PHY_SITEDeletePopupComponent } from './phy-site-delete-dialog.component';

export const pHY_SITERoute: Routes = [
    {
        path: 'phy-site',
        component: PHY_SITEComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PHY_SITES'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'phy-site/:id',
        component: PHY_SITEDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PHY_SITES'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pHY_SITEPopupRoute: Routes = [
    {
        path: 'phy-site-new',
        component: PHY_SITEPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PHY_SITES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phy-site/:id/edit',
        component: PHY_SITEPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PHY_SITES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phy-site/:id/delete',
        component: PHY_SITEDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PHY_SITES'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
