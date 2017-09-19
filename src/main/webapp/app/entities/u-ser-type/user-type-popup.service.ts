import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { USER_TYPE } from './user-type.model';
import { USER_TYPEService } from './user-type.service';

@Injectable()
export class USER_TYPEPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private uSER_TYPEService: USER_TYPEService

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
                this.uSER_TYPEService.find(id).subscribe((uSER_TYPE) => {
                    this.ngbModalRef = this.uSER_TYPEModalRef(component, uSER_TYPE);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.uSER_TYPEModalRef(component, new USER_TYPE());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    uSER_TYPEModalRef(component: Component, uSER_TYPE: USER_TYPE): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.uSER_TYPE = uSER_TYPE;
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
