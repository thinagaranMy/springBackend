import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { STATE } from './state.model';
import { STATEPopupService } from './state-popup.service';
import { STATEService } from './state.service';

@Component({
    selector: 'jhi-state-dialog',
    templateUrl: './state-dialog.component.html'
})
export class STATEDialogComponent implements OnInit {

    sTATE: STATE;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sTATEService: STATEService,
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
        if (this.sTATE.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sTATEService.update(this.sTATE));
        } else {
            this.subscribeToSaveResponse(
                this.sTATEService.create(this.sTATE));
        }
    }

    private subscribeToSaveResponse(result: Observable<STATE>) {
        result.subscribe((res: STATE) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: STATE) {
        this.eventManager.broadcast({ name: 'sTATEListModification', content: 'OK'});
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
    selector: 'jhi-state-popup',
    template: ''
})
export class STATEPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sTATEPopupService: STATEPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sTATEPopupService
                    .open(STATEDialogComponent as Component, params['id']);
            } else {
                this.sTATEPopupService
                    .open(STATEDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
