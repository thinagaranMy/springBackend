import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CUSTOM_USER } from './custom-user.model';
import { CUSTOM_USERPopupService } from './custom-user-popup.service';
import { CUSTOM_USERService } from './custom-user.service';

@Component({
    selector: 'jhi-custom-user-delete-dialog',
    templateUrl: './custom-user-delete-dialog.component.html'
})
export class CUSTOM_USERDeleteDialogComponent {

    cUSTOM_USER: CUSTOM_USER;

    constructor(
        private cUSTOM_USERService: CUSTOM_USERService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cUSTOM_USERService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cUSTOM_USERListModification',
                content: 'Deleted an cUSTOM_USER'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-custom-user-delete-popup',
    template: ''
})
export class CUSTOM_USERDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cUSTOM_USERPopupService: CUSTOM_USERPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cUSTOM_USERPopupService
                .open(CUSTOM_USERDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
