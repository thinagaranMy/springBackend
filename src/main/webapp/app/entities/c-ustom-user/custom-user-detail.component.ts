import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { CUSTOM_USER } from './custom-user.model';
import { CUSTOM_USERService } from './custom-user.service';

@Component({
    selector: 'jhi-custom-user-detail',
    templateUrl: './custom-user-detail.component.html'
})
export class CUSTOM_USERDetailComponent implements OnInit, OnDestroy {

    cUSTOM_USER: CUSTOM_USER;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cUSTOM_USERService: CUSTOM_USERService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCUSTOM_USERS();
    }

    load(id) {
        this.cUSTOM_USERService.find(id).subscribe((cUSTOM_USER) => {
            this.cUSTOM_USER = cUSTOM_USER;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCUSTOM_USERS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cUSTOM_USERListModification',
            (response) => this.load(this.cUSTOM_USER.id)
        );
    }
}
