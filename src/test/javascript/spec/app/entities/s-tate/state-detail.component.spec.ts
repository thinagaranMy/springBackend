/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { STATEDetailComponent } from '../../../../../../main/webapp/app/entities/s-tate/state-detail.component';
import { STATEService } from '../../../../../../main/webapp/app/entities/s-tate/state.service';
import { STATE } from '../../../../../../main/webapp/app/entities/s-tate/state.model';

describe('Component Tests', () => {

    describe('STATE Management Detail Component', () => {
        let comp: STATEDetailComponent;
        let fixture: ComponentFixture<STATEDetailComponent>;
        let service: STATEService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [STATEDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    STATEService,
                    JhiEventManager
                ]
            }).overrideTemplate(STATEDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(STATEDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(STATEService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new STATE(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sTATE).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
