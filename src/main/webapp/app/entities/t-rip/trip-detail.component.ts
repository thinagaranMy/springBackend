import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TRIP } from './trip.model';
import { TRIPService } from './trip.service';

@Component({
    selector: 'jhi-trip-detail',
    templateUrl: './trip-detail.component.html'
})
export class TRIPDetailComponent implements OnInit, OnDestroy {

    tRIP: TRIP;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tRIPService: TRIPService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTRIPS();
    }

    load(id) {
        this.tRIPService.find(id).subscribe((tRIP) => {
            this.tRIP = tRIP;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTRIPS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tRIPListModification',
            (response) => this.load(this.tRIP.id)
        );
    }
}
