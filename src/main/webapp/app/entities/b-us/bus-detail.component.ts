import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { BUS } from './bus.model';
import { BUSService } from './bus.service';

@Component({
    selector: 'jhi-bus-detail',
    templateUrl: './bus-detail.component.html'
})
export class BUSDetailComponent implements OnInit, OnDestroy {

    bUS: BUS;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bUSService: BUSService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBUSES();
    }

    load(id) {
        this.bUSService.find(id).subscribe((bUS) => {
            this.bUS = bUS;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBUSES() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bUSListModification',
            (response) => this.load(this.bUS.id)
        );
    }
}
