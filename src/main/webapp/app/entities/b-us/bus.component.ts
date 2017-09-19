import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { BUS } from './bus.model';
import { BUSService } from './bus.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-bus',
    templateUrl: './bus.component.html'
})
export class BUSComponent implements OnInit, OnDestroy {
buses: BUS[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bUSService: BUSService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.bUSService.query().subscribe(
            (res: ResponseWrapper) => {
                this.buses = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBUSES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: BUS) {
        return item.id;
    }
    registerChangeInBUSES() {
        this.eventSubscriber = this.eventManager.subscribe('bUSListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
