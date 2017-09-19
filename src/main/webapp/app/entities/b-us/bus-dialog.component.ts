import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BUS } from './bus.model';
import { BUSPopupService } from './bus-popup.service';
import { BUSService } from './bus.service';

@Component({
    selector: 'jhi-bus-dialog',
    templateUrl: './bus-dialog.component.html'
})
export class BUSDialogComponent implements OnInit {

    bUS: BUS;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bUSService: BUSService,
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
        if (this.bUS.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bUSService.update(this.bUS));
        } else {
            this.subscribeToSaveResponse(
                this.bUSService.create(this.bUS));
        }
    }

    private subscribeToSaveResponse(result: Observable<BUS>) {
        result.subscribe((res: BUS) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BUS) {
        this.eventManager.broadcast({ name: 'bUSListModification', content: 'OK'});
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
    selector: 'jhi-bus-popup',
    template: ''
})
export class BUSPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bUSPopupService: BUSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bUSPopupService
                    .open(BUSDialogComponent as Component, params['id']);
            } else {
                this.bUSPopupService
                    .open(BUSDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
