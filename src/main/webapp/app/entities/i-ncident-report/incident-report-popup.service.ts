import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { INCIDENT_REPORT } from './incident-report.model';
import { INCIDENT_REPORTService } from './incident-report.service';

@Injectable()
export class INCIDENT_REPORTPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private iNCIDENT_REPORTService: INCIDENT_REPORTService

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
                this.iNCIDENT_REPORTService.find(id).subscribe((iNCIDENT_REPORT) => {
                    this.ngbModalRef = this.iNCIDENT_REPORTModalRef(component, iNCIDENT_REPORT);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.iNCIDENT_REPORTModalRef(component, new INCIDENT_REPORT());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    iNCIDENT_REPORTModalRef(component: Component, iNCIDENT_REPORT: INCIDENT_REPORT): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.iNCIDENT_REPORT = iNCIDENT_REPORT;
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
