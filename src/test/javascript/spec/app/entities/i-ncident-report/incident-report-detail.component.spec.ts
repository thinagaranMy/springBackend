/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { INCIDENT_REPORTDetailComponent } from '../../../../../../main/webapp/app/entities/i-ncident-report/incident-report-detail.component';
import { INCIDENT_REPORTService } from '../../../../../../main/webapp/app/entities/i-ncident-report/incident-report.service';
import { INCIDENT_REPORT } from '../../../../../../main/webapp/app/entities/i-ncident-report/incident-report.model';

describe('Component Tests', () => {

    describe('INCIDENT_REPORT Management Detail Component', () => {
        let comp: INCIDENT_REPORTDetailComponent;
        let fixture: ComponentFixture<INCIDENT_REPORTDetailComponent>;
        let service: INCIDENT_REPORTService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [INCIDENT_REPORTDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    INCIDENT_REPORTService,
                    JhiEventManager
                ]
            }).overrideTemplate(INCIDENT_REPORTDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(INCIDENT_REPORTDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(INCIDENT_REPORTService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new INCIDENT_REPORT(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.iNCIDENT_REPORT).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
