import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INCIDENT_REPORT } from './incident-report.model';
import { INCIDENT_REPORTPopupService } from './incident-report-popup.service';
import { INCIDENT_REPORTService } from './incident-report.service';

@Component({
    selector: 'jhi-incident-report-dialog',
    templateUrl: './incident-report-dialog.component.html'
})
export class INCIDENT_REPORTDialogComponent implements OnInit {

    iNCIDENT_REPORT: INCIDENT_REPORT;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private iNCIDENT_REPORTService: INCIDENT_REPORTService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.iNCIDENT_REPORT.id !== undefined) {
            this.subscribeToSaveResponse(
                this.iNCIDENT_REPORTService.update(this.iNCIDENT_REPORT));
        } else {
            this.subscribeToSaveResponse(
                this.iNCIDENT_REPORTService.create(this.iNCIDENT_REPORT));
        }
    }

    private subscribeToSaveResponse(result: Observable<INCIDENT_REPORT>) {
        result.subscribe((res: INCIDENT_REPORT) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: INCIDENT_REPORT) {
        this.eventManager.broadcast({ name: 'iNCIDENT_REPORTListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-incident-report-popup',
    template: ''
})
export class INCIDENT_REPORTPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private iNCIDENT_REPORTPopupService: INCIDENT_REPORTPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.iNCIDENT_REPORTPopupService
                    .open(INCIDENT_REPORTDialogComponent as Component, params['id']);
            } else {
                this.iNCIDENT_REPORTPopupService
                    .open(INCIDENT_REPORTDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
