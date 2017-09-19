import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { CUSTOM_USER } from './custom-user.model';
import { CUSTOM_USERService } from './custom-user.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-custom-user',
    templateUrl: './custom-user.component.html'
})
export class CUSTOM_USERComponent implements OnInit, OnDestroy {
cUSTOM_USERS: CUSTOM_USER[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cUSTOM_USERService: CUSTOM_USERService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.cUSTOM_USERService.query().subscribe(
            (res: ResponseWrapper) => {
                this.cUSTOM_USERS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCUSTOM_USERS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CUSTOM_USER) {
        return item.id;
    }
    registerChangeInCUSTOM_USERS() {
        this.eventSubscriber = this.eventManager.subscribe('cUSTOM_USERListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
