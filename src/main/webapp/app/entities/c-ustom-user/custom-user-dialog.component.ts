import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CUSTOM_USER } from './custom-user.model';
import { CUSTOM_USERPopupService } from './custom-user-popup.service';
import { CUSTOM_USERService } from './custom-user.service';

@Component({
    selector: 'jhi-custom-user-dialog',
    templateUrl: './custom-user-dialog.component.html'
})
export class CUSTOM_USERDialogComponent implements OnInit {

    cUSTOM_USER: CUSTOM_USER;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private cUSTOM_USERService: CUSTOM_USERService,
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
        if (this.cUSTOM_USER.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cUSTOM_USERService.update(this.cUSTOM_USER));
        } else {
            this.subscribeToSaveResponse(
                this.cUSTOM_USERService.create(this.cUSTOM_USER));
        }
    }

    private subscribeToSaveResponse(result: Observable<CUSTOM_USER>) {
        result.subscribe((res: CUSTOM_USER) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CUSTOM_USER) {
        this.eventManager.broadcast({ name: 'cUSTOM_USERListModification', content: 'OK'});
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
    selector: 'jhi-custom-user-popup',
    template: ''
})
export class CUSTOM_USERPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cUSTOM_USERPopupService: CUSTOM_USERPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cUSTOM_USERPopupService
                    .open(CUSTOM_USERDialogComponent as Component, params['id']);
            } else {
                this.cUSTOM_USERPopupService
                    .open(CUSTOM_USERDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
