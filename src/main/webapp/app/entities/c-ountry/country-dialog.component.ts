import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { COUNTRY } from './country.model';
import { COUNTRYPopupService } from './country-popup.service';
import { COUNTRYService } from './country.service';

@Component({
    selector: 'jhi-country-dialog',
    templateUrl: './country-dialog.component.html'
})
export class COUNTRYDialogComponent implements OnInit {

    cOUNTRY: COUNTRY;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private cOUNTRYService: COUNTRYService,
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
        if (this.cOUNTRY.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cOUNTRYService.update(this.cOUNTRY));
        } else {
            this.subscribeToSaveResponse(
                this.cOUNTRYService.create(this.cOUNTRY));
        }
    }

    private subscribeToSaveResponse(result: Observable<COUNTRY>) {
        result.subscribe((res: COUNTRY) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: COUNTRY) {
        this.eventManager.broadcast({ name: 'cOUNTRYListModification', content: 'OK'});
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
    selector: 'jhi-country-popup',
    template: ''
})
export class COUNTRYPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cOUNTRYPopupService: COUNTRYPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cOUNTRYPopupService
                    .open(COUNTRYDialogComponent as Component, params['id']);
            } else {
                this.cOUNTRYPopupService
                    .open(COUNTRYDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
