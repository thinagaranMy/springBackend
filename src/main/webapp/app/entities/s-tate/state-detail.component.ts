import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { STATE } from './state.model';
import { STATEService } from './state.service';

@Component({
    selector: 'jhi-state-detail',
    templateUrl: './state-detail.component.html'
})
export class STATEDetailComponent implements OnInit, OnDestroy {

    sTATE: STATE;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sTATEService: STATEService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSTATES();
    }

    load(id) {
        this.sTATEService.find(id).subscribe((sTATE) => {
            this.sTATE = sTATE;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSTATES() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sTATEListModification',
            (response) => this.load(this.sTATE.id)
        );
    }
}
