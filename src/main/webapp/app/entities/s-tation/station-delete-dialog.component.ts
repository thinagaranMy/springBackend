import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { STATION } from './station.model';
import { STATIONPopupService } from './station-popup.service';
import { STATIONService } from './station.service';

@Component({
    selector: 'jhi-station-delete-dialog',
    templateUrl: './station-delete-dialog.component.html'
})
export class STATIONDeleteDialogComponent {

    sTATION: STATION;

    constructor(
        private sTATIONService: STATIONService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sTATIONService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sTATIONListModification',
                content: 'Deleted an sTATION'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-station-delete-popup',
    template: ''
})
export class STATIONDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sTATIONPopupService: STATIONPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sTATIONPopupService
                .open(STATIONDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
