import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { STATION } from './station.model';
import { STATIONService } from './station.service';

@Component({
    selector: 'jhi-station-detail',
    templateUrl: './station-detail.component.html'
})
export class STATIONDetailComponent implements OnInit, OnDestroy {

    sTATION: STATION;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sTATIONService: STATIONService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSTATIONS();
    }

    load(id) {
        this.sTATIONService.find(id).subscribe((sTATION) => {
            this.sTATION = sTATION;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSTATIONS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sTATIONListModification',
            (response) => this.load(this.sTATION.id)
        );
    }
}
