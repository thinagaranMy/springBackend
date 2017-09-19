import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PHY_SITE } from './phy-site.model';
import { PHY_SITEPopupService } from './phy-site-popup.service';
import { PHY_SITEService } from './phy-site.service';

@Component({
    selector: 'jhi-phy-site-delete-dialog',
    templateUrl: './phy-site-delete-dialog.component.html'
})
export class PHY_SITEDeleteDialogComponent {

    pHY_SITE: PHY_SITE;

    constructor(
        private pHY_SITEService: PHY_SITEService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pHY_SITEService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pHY_SITEListModification',
                content: 'Deleted an pHY_SITE'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phy-site-delete-popup',
    template: ''
})
export class PHY_SITEDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pHY_SITEPopupService: PHY_SITEPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pHY_SITEPopupService
                .open(PHY_SITEDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
