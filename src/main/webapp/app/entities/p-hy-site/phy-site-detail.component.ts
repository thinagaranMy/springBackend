import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PHY_SITE } from './phy-site.model';
import { PHY_SITEService } from './phy-site.service';

@Component({
    selector: 'jhi-phy-site-detail',
    templateUrl: './phy-site-detail.component.html'
})
export class PHY_SITEDetailComponent implements OnInit, OnDestroy {

    pHY_SITE: PHY_SITE;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pHY_SITEService: PHY_SITEService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPHY_SITES();
    }

    load(id) {
        this.pHY_SITEService.find(id).subscribe((pHY_SITE) => {
            this.pHY_SITE = pHY_SITE;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPHY_SITES() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pHY_SITEListModification',
            (response) => this.load(this.pHY_SITE.id)
        );
    }
}
