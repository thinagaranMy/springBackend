/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BUSDetailComponent } from '../../../../../../main/webapp/app/entities/b-us/bus-detail.component';
import { BUSService } from '../../../../../../main/webapp/app/entities/b-us/bus.service';
import { BUS } from '../../../../../../main/webapp/app/entities/b-us/bus.model';

describe('Component Tests', () => {

    describe('BUS Management Detail Component', () => {
        let comp: BUSDetailComponent;
        let fixture: ComponentFixture<BUSDetailComponent>;
        let service: BUSService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [BUSDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BUSService,
                    JhiEventManager
                ]
            }).overrideTemplate(BUSDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BUSDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BUSService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BUS(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bUS).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
