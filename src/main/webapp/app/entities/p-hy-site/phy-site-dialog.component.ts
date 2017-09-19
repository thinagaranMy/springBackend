import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PHY_SITE } from './phy-site.model';
import { PHY_SITEPopupService } from './phy-site-popup.service';
import { PHY_SITEService } from './phy-site.service';

@Component({
    selector: 'jhi-phy-site-dialog',
    templateUrl: './phy-site-dialog.component.html'
})
export class PHY_SITEDialogComponent implements OnInit {

    pHY_SITE: PHY_SITE;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pHY_SITEService: PHY_SITEService,
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
        if (this.pHY_SITE.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pHY_SITEService.update(this.pHY_SITE));
        } else {
            this.subscribeToSaveResponse(
                this.pHY_SITEService.create(this.pHY_SITE));
        }
    }

    private subscribeToSaveResponse(result: Observable<PHY_SITE>) {
        result.subscribe((res: PHY_SITE) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PHY_SITE) {
        this.eventManager.broadcast({ name: 'pHY_SITEListModification', content: 'OK'});
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
    selector: 'jhi-phy-site-popup',
    template: ''
})
export class PHY_SITEPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pHY_SITEPopupService: PHY_SITEPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pHY_SITEPopupService
                    .open(PHY_SITEDialogComponent as Component, params['id']);
            } else {
                this.pHY_SITEPopupService
                    .open(PHY_SITEDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
