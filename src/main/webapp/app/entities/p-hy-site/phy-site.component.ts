import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { PHY_SITE } from './phy-site.model';
import { PHY_SITEService } from './phy-site.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-phy-site',
    templateUrl: './phy-site.component.html'
})
export class PHY_SITEComponent implements OnInit, OnDestroy {
pHY_SITES: PHY_SITE[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pHY_SITEService: PHY_SITEService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pHY_SITEService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pHY_SITES = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPHY_SITES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PHY_SITE) {
        return item.id;
    }
    registerChangeInPHY_SITES() {
        this.eventSubscriber = this.eventManager.subscribe('pHY_SITEListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
