import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { COUNTRY } from './country.model';
import { COUNTRYService } from './country.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-country',
    templateUrl: './country.component.html'
})
export class COUNTRYComponent implements OnInit, OnDestroy {
cOUNTRIES: COUNTRY[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cOUNTRYService: COUNTRYService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.cOUNTRYService.query().subscribe(
            (res: ResponseWrapper) => {
                this.cOUNTRIES = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCOUNTRIES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: COUNTRY) {
        return item.id;
    }
    registerChangeInCOUNTRIES() {
        this.eventSubscriber = this.eventManager.subscribe('cOUNTRYListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
