import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { COUNTRY } from './country.model';
import { COUNTRYPopupService } from './country-popup.service';
import { COUNTRYService } from './country.service';

@Component({
    selector: 'jhi-country-delete-dialog',
    templateUrl: './country-delete-dialog.component.html'
})
export class COUNTRYDeleteDialogComponent {

    cOUNTRY: COUNTRY;

    constructor(
        private cOUNTRYService: COUNTRYService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cOUNTRYService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cOUNTRYListModification',
                content: 'Deleted an cOUNTRY'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-country-delete-popup',
    template: ''
})
export class COUNTRYDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cOUNTRYPopupService: COUNTRYPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cOUNTRYPopupService
                .open(COUNTRYDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
