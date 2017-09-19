import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TRIP } from './trip.model';
import { TRIPPopupService } from './trip-popup.service';
import { TRIPService } from './trip.service';

@Component({
    selector: 'jhi-trip-dialog',
    templateUrl: './trip-dialog.component.html'
})
export class TRIPDialogComponent implements OnInit {

    tRIP: TRIP;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tRIPService: TRIPService,
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
        if (this.tRIP.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tRIPService.update(this.tRIP));
        } else {
            this.subscribeToSaveResponse(
                this.tRIPService.create(this.tRIP));
        }
    }

    private subscribeToSaveResponse(result: Observable<TRIP>) {
        result.subscribe((res: TRIP) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TRIP) {
        this.eventManager.broadcast({ name: 'tRIPListModification', content: 'OK'});
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
    selector: 'jhi-trip-popup',
    template: ''
})
export class TRIPPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tRIPPopupService: TRIPPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tRIPPopupService
                    .open(TRIPDialogComponent as Component, params['id']);
            } else {
                this.tRIPPopupService
                    .open(TRIPDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
