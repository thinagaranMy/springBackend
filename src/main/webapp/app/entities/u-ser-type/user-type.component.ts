import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { USER_TYPE } from './user-type.model';
import { USER_TYPEService } from './user-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-user-type',
    templateUrl: './user-type.component.html'
})
export class USER_TYPEComponent implements OnInit, OnDestroy {
uSER_TYPES: USER_TYPE[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private uSER_TYPEService: USER_TYPEService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.uSER_TYPEService.query().subscribe(
            (res: ResponseWrapper) => {
                this.uSER_TYPES = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUSER_TYPES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: USER_TYPE) {
        return item.id;
    }
    registerChangeInUSER_TYPES() {
        this.eventSubscriber = this.eventManager.subscribe('uSER_TYPEListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
