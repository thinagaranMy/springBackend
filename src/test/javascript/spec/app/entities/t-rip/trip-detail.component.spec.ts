/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TRIPDetailComponent } from '../../../../../../main/webapp/app/entities/t-rip/trip-detail.component';
import { TRIPService } from '../../../../../../main/webapp/app/entities/t-rip/trip.service';
import { TRIP } from '../../../../../../main/webapp/app/entities/t-rip/trip.model';

describe('Component Tests', () => {

    describe('TRIP Management Detail Component', () => {
        let comp: TRIPDetailComponent;
        let fixture: ComponentFixture<TRIPDetailComponent>;
        let service: TRIPService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [TRIPDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TRIPService,
                    JhiEventManager
                ]
            }).overrideTemplate(TRIPDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TRIPDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TRIPService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TRIP(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tRIP).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
