import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { STATE } from './state.model';
import { STATEPopupService } from './state-popup.service';
import { STATEService } from './state.service';

@Component({
    selector: 'jhi-state-delete-dialog',
    templateUrl: './state-delete-dialog.component.html'
})
export class STATEDeleteDialogComponent {

    sTATE: STATE;

    constructor(
        private sTATEService: STATEService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sTATEService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sTATEListModification',
                content: 'Deleted an sTATE'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-state-delete-popup',
    template: ''
})
export class STATEDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sTATEPopupService: STATEPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sTATEPopupService
                .open(STATEDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
