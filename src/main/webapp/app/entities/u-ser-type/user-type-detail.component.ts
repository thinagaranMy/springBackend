import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { USER_TYPE } from './user-type.model';
import { USER_TYPEService } from './user-type.service';

@Component({
    selector: 'jhi-user-type-detail',
    templateUrl: './user-type-detail.component.html'
})
export class USER_TYPEDetailComponent implements OnInit, OnDestroy {

    uSER_TYPE: USER_TYPE;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private uSER_TYPEService: USER_TYPEService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUSER_TYPES();
    }

    load(id) {
        this.uSER_TYPEService.find(id).subscribe((uSER_TYPE) => {
            this.uSER_TYPE = uSER_TYPE;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUSER_TYPES() {
        this.eventSubscriber = this.eventManager.subscribe(
            'uSER_TYPEListModification',
            (response) => this.load(this.uSER_TYPE.id)
        );
    }
}
