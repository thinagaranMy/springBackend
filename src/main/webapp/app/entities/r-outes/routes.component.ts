import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ROUTES } from './routes.model';
import { ROUTESService } from './routes.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-routes',
    templateUrl: './routes.component.html'
})
export class ROUTESComponent implements OnInit, OnDestroy {
rOUTES: ROUTES[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rOUTESService: ROUTESService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.rOUTESService.query().subscribe(
            (res: ResponseWrapper) => {
                this.rOUTES = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInROUTES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ROUTES) {
        return item.id;
    }
    registerChangeInROUTES() {
        this.eventSubscriber = this.eventManager.subscribe('rOUTESListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
