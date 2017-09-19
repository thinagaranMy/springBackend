import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { STATION } from './station.model';
import { STATIONPopupService } from './station-popup.service';
import { STATIONService } from './station.service';

@Component({
    selector: 'jhi-station-dialog',
    templateUrl: './station-dialog.component.html'
})
export class STATIONDialogComponent implements OnInit {

    sTATION: STATION;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sTATIONService: STATIONService,
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
        if (this.sTATION.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sTATIONService.update(this.sTATION));
        } else {
            this.subscribeToSaveResponse(
                this.sTATIONService.create(this.sTATION));
        }
    }

    private subscribeToSaveResponse(result: Observable<STATION>) {
        result.subscribe((res: STATION) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: STATION) {
        this.eventManager.broadcast({ name: 'sTATIONListModification', content: 'OK'});
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
    selector: 'jhi-station-popup',
    template: ''
})
export class STATIONPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sTATIONPopupService: STATIONPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sTATIONPopupService
                    .open(STATIONDialogComponent as Component, params['id']);
            } else {
                this.sTATIONPopupService
                    .open(STATIONDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
