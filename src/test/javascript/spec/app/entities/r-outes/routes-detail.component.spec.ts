/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ROUTESDetailComponent } from '../../../../../../main/webapp/app/entities/r-outes/routes-detail.component';
import { ROUTESService } from '../../../../../../main/webapp/app/entities/r-outes/routes.service';
import { ROUTES } from '../../../../../../main/webapp/app/entities/r-outes/routes.model';

describe('Component Tests', () => {

    describe('ROUTES Management Detail Component', () => {
        let comp: ROUTESDetailComponent;
        let fixture: ComponentFixture<ROUTESDetailComponent>;
        let service: ROUTESService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [ROUTESDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ROUTESService,
                    JhiEventManager
                ]
            }).overrideTemplate(ROUTESDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ROUTESDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ROUTESService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ROUTES(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rOUTES).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
