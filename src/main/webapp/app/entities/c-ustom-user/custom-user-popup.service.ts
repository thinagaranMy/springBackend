import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CUSTOM_USER } from './custom-user.model';
import { CUSTOM_USERService } from './custom-user.service';

@Injectable()
export class CUSTOM_USERPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cUSTOM_USERService: CUSTOM_USERService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.cUSTOM_USERService.find(id).subscribe((cUSTOM_USER) => {
                    this.ngbModalRef = this.cUSTOM_USERModalRef(component, cUSTOM_USER);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.cUSTOM_USERModalRef(component, new CUSTOM_USER());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    cUSTOM_USERModalRef(component: Component, cUSTOM_USER: CUSTOM_USER): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cUSTOM_USER = cUSTOM_USER;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
