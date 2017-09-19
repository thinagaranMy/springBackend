import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { STATE } from './state.model';
import { STATEService } from './state.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-state',
    templateUrl: './state.component.html'
})
export class STATEComponent implements OnInit, OnDestroy {
sTATES: STATE[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sTATEService: STATEService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sTATEService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sTATES = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSTATES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: STATE) {
        return item.id;
    }
    registerChangeInSTATES() {
        this.eventSubscriber = this.eventManager.subscribe('sTATEListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
