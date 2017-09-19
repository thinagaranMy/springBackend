import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BUS } from './bus.model';
import { BUSPopupService } from './bus-popup.service';
import { BUSService } from './bus.service';

@Component({
    selector: 'jhi-bus-delete-dialog',
    templateUrl: './bus-delete-dialog.component.html'
})
export class BUSDeleteDialogComponent {

    bUS: BUS;

    constructor(
        private bUSService: BUSService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bUSService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bUSListModification',
                content: 'Deleted an bUS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bus-delete-popup',
    template: ''
})
export class BUSDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bUSPopupService: BUSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bUSPopupService
                .open(BUSDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
