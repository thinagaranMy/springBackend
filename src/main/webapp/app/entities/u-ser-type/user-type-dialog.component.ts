import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { USER_TYPE } from './user-type.model';
import { USER_TYPEPopupService } from './user-type-popup.service';
import { USER_TYPEService } from './user-type.service';

@Component({
    selector: 'jhi-user-type-dialog',
    templateUrl: './user-type-dialog.component.html'
})
export class USER_TYPEDialogComponent implements OnInit {

    uSER_TYPE: USER_TYPE;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private uSER_TYPEService: USER_TYPEService,
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
        if (this.uSER_TYPE.id !== undefined) {
            this.subscribeToSaveResponse(
                this.uSER_TYPEService.update(this.uSER_TYPE));
        } else {
            this.subscribeToSaveResponse(
                this.uSER_TYPEService.create(this.uSER_TYPE));
        }
    }

    private subscribeToSaveResponse(result: Observable<USER_TYPE>) {
        result.subscribe((res: USER_TYPE) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: USER_TYPE) {
        this.eventManager.broadcast({ name: 'uSER_TYPEListModification', content: 'OK'});
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
    selector: 'jhi-user-type-popup',
    template: ''
})
export class USER_TYPEPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uSER_TYPEPopupService: USER_TYPEPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.uSER_TYPEPopupService
                    .open(USER_TYPEDialogComponent as Component, params['id']);
            } else {
                this.uSER_TYPEPopupService
                    .open(USER_TYPEDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
