import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ROUTES } from './routes.model';
import { ROUTESPopupService } from './routes-popup.service';
import { ROUTESService } from './routes.service';

@Component({
    selector: 'jhi-routes-delete-dialog',
    templateUrl: './routes-delete-dialog.component.html'
})
export class ROUTESDeleteDialogComponent {

    rOUTES: ROUTES;

    constructor(
        private rOUTESService: ROUTESService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rOUTESService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rOUTESListModification',
                content: 'Deleted an rOUTES'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-routes-delete-popup',
    template: ''
})
export class ROUTESDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rOUTESPopupService: ROUTESPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rOUTESPopupService
                .open(ROUTESDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
