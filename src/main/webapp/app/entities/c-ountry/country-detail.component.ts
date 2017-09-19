import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { COUNTRY } from './country.model';
import { COUNTRYService } from './country.service';

@Component({
    selector: 'jhi-country-detail',
    templateUrl: './country-detail.component.html'
})
export class COUNTRYDetailComponent implements OnInit, OnDestroy {

    cOUNTRY: COUNTRY;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cOUNTRYService: COUNTRYService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCOUNTRIES();
    }

    load(id) {
        this.cOUNTRYService.find(id).subscribe((cOUNTRY) => {
            this.cOUNTRY = cOUNTRY;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCOUNTRIES() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cOUNTRYListModification',
            (response) => this.load(this.cOUNTRY.id)
        );
    }
}
