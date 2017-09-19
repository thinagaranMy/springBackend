import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ROUTES } from './routes.model';
import { ROUTESService } from './routes.service';

@Component({
    selector: 'jhi-routes-detail',
    templateUrl: './routes-detail.component.html'
})
export class ROUTESDetailComponent implements OnInit, OnDestroy {

    rOUTES: ROUTES;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rOUTESService: ROUTESService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInROUTES();
    }

    load(id) {
        this.rOUTESService.find(id).subscribe((rOUTES) => {
            this.rOUTES = rOUTES;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInROUTES() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rOUTESListModification',
            (response) => this.load(this.rOUTES.id)
        );
    }
}
