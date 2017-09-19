import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { TRIP } from './trip.model';
import { TRIPService } from './trip.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-trip',
    templateUrl: './trip.component.html'
})
export class TRIPComponent implements OnInit, OnDestroy {
tRIPS: TRIP[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tRIPService: TRIPService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tRIPService.query().subscribe(
            (res: ResponseWrapper) => {
                this.tRIPS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTRIPS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TRIP) {
        return item.id;
    }
    registerChangeInTRIPS() {
        this.eventSubscriber = this.eventManager.subscribe('tRIPListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
