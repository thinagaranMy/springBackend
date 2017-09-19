/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { STATIONDetailComponent } from '../../../../../../main/webapp/app/entities/s-tation/station-detail.component';
import { STATIONService } from '../../../../../../main/webapp/app/entities/s-tation/station.service';
import { STATION } from '../../../../../../main/webapp/app/entities/s-tation/station.model';

describe('Component Tests', () => {

    describe('STATION Management Detail Component', () => {
        let comp: STATIONDetailComponent;
        let fixture: ComponentFixture<STATIONDetailComponent>;
        let service: STATIONService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [STATIONDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    STATIONService,
                    JhiEventManager
                ]
            }).overrideTemplate(STATIONDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(STATIONDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(STATIONService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new STATION(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sTATION).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
