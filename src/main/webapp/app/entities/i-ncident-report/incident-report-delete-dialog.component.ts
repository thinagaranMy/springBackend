import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INCIDENT_REPORT } from './incident-report.model';
import { INCIDENT_REPORTPopupService } from './incident-report-popup.service';
import { INCIDENT_REPORTService } from './incident-report.service';

@Component({
    selector: 'jhi-incident-report-delete-dialog',
    templateUrl: './incident-report-delete-dialog.component.html'
})
export class INCIDENT_REPORTDeleteDialogComponent {

    iNCIDENT_REPORT: INCIDENT_REPORT;

    constructor(
        private iNCIDENT_REPORTService: INCIDENT_REPORTService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.iNCIDENT_REPORTService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'iNCIDENT_REPORTListModification',
                content: 'Deleted an iNCIDENT_REPORT'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-incident-report-delete-popup',
    template: ''
})
export class INCIDENT_REPORTDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private iNCIDENT_REPORTPopupService: INCIDENT_REPORTPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.iNCIDENT_REPORTPopupService
                .open(INCIDENT_REPORTDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
