import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { STATION } from './station.model';
import { STATIONService } from './station.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-station',
    templateUrl: './station.component.html'
})
export class STATIONComponent implements OnInit, OnDestroy {
sTATIONS: STATION[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sTATIONService: STATIONService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sTATIONService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sTATIONS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSTATIONS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: STATION) {
        return item.id;
    }
    registerChangeInSTATIONS() {
        this.eventSubscriber = this.eventManager.subscribe('sTATIONListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
