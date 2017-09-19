import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ROUTES } from './routes.model';
import { ROUTESPopupService } from './routes-popup.service';
import { ROUTESService } from './routes.service';

@Component({
    selector: 'jhi-routes-dialog',
    templateUrl: './routes-dialog.component.html'
})
export class ROUTESDialogComponent implements OnInit {

    rOUTES: ROUTES;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private rOUTESService: ROUTESService,
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
        if (this.rOUTES.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rOUTESService.update(this.rOUTES));
        } else {
            this.subscribeToSaveResponse(
                this.rOUTESService.create(this.rOUTES));
        }
    }

    private subscribeToSaveResponse(result: Observable<ROUTES>) {
        result.subscribe((res: ROUTES) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ROUTES) {
        this.eventManager.broadcast({ name: 'rOUTESListModification', content: 'OK'});
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
    selector: 'jhi-routes-popup',
    template: ''
})
export class ROUTESPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rOUTESPopupService: ROUTESPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rOUTESPopupService
                    .open(ROUTESDialogComponent as Component, params['id']);
            } else {
                this.rOUTESPopupService
                    .open(ROUTESDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
