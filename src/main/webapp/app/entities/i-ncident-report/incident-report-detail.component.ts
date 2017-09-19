import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { INCIDENT_REPORT } from './incident-report.model';
import { INCIDENT_REPORTService } from './incident-report.service';

@Component({
    selector: 'jhi-incident-report-detail',
    templateUrl: './incident-report-detail.component.html'
})
export class INCIDENT_REPORTDetailComponent implements OnInit, OnDestroy {

    iNCIDENT_REPORT: INCIDENT_REPORT;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private iNCIDENT_REPORTService: INCIDENT_REPORTService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInINCIDENT_REPORTS();
    }

    load(id) {
        this.iNCIDENT_REPORTService.find(id).subscribe((iNCIDENT_REPORT) => {
            this.iNCIDENT_REPORT = iNCIDENT_REPORT;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInINCIDENT_REPORTS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'iNCIDENT_REPORTListModification',
            (response) => this.load(this.iNCIDENT_REPORT.id)
        );
    }
}
