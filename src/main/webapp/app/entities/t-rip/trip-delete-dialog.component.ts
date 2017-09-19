import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TRIP } from './trip.model';
import { TRIPPopupService } from './trip-popup.service';
import { TRIPService } from './trip.service';

@Component({
    selector: 'jhi-trip-delete-dialog',
    templateUrl: './trip-delete-dialog.component.html'
})
export class TRIPDeleteDialogComponent {

    tRIP: TRIP;

    constructor(
        private tRIPService: TRIPService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tRIPService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tRIPListModification',
                content: 'Deleted an tRIP'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-trip-delete-popup',
    template: ''
})
export class TRIPDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tRIPPopupService: TRIPPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tRIPPopupService
                .open(TRIPDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
