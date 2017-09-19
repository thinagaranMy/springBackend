import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { USER_TYPE } from './user-type.model';
import { USER_TYPEPopupService } from './user-type-popup.service';
import { USER_TYPEService } from './user-type.service';

@Component({
    selector: 'jhi-user-type-delete-dialog',
    templateUrl: './user-type-delete-dialog.component.html'
})
export class USER_TYPEDeleteDialogComponent {

    uSER_TYPE: USER_TYPE;

    constructor(
        private uSER_TYPEService: USER_TYPEService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.uSER_TYPEService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'uSER_TYPEListModification',
                content: 'Deleted an uSER_TYPE'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-type-delete-popup',
    template: ''
})
export class USER_TYPEDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uSER_TYPEPopupService: USER_TYPEPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.uSER_TYPEPopupService
                .open(USER_TYPEDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
