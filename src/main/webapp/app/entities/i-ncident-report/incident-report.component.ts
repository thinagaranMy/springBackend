import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { INCIDENT_REPORT } from './incident-report.model';
import { INCIDENT_REPORTService } from './incident-report.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-incident-report',
    templateUrl: './incident-report.component.html'
})
export class INCIDENT_REPORTComponent implements OnInit, OnDestroy {
iNCIDENT_REPORTS: INCIDENT_REPORT[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private iNCIDENT_REPORTService: INCIDENT_REPORTService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.iNCIDENT_REPORTService.query().subscribe(
            (res: ResponseWrapper) => {
                this.iNCIDENT_REPORTS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInINCIDENT_REPORTS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INCIDENT_REPORT) {
        return item.id;
    }
    registerChangeInINCIDENT_REPORTS() {
        this.eventSubscriber = this.eventManager.subscribe('iNCIDENT_REPORTListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
