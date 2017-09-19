/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CUSTOM_USERDetailComponent } from '../../../../../../main/webapp/app/entities/c-ustom-user/custom-user-detail.component';
import { CUSTOM_USERService } from '../../../../../../main/webapp/app/entities/c-ustom-user/custom-user.service';
import { CUSTOM_USER } from '../../../../../../main/webapp/app/entities/c-ustom-user/custom-user.model';

describe('Component Tests', () => {

    describe('CUSTOM_USER Management Detail Component', () => {
        let comp: CUSTOM_USERDetailComponent;
        let fixture: ComponentFixture<CUSTOM_USERDetailComponent>;
        let service: CUSTOM_USERService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [CUSTOM_USERDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CUSTOM_USERService,
                    JhiEventManager
                ]
            }).overrideTemplate(CUSTOM_USERDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CUSTOM_USERDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CUSTOM_USERService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CUSTOM_USER(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cUSTOM_USER).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
