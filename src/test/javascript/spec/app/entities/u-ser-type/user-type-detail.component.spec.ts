/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { USER_TYPEDetailComponent } from '../../../../../../main/webapp/app/entities/u-ser-type/user-type-detail.component';
import { USER_TYPEService } from '../../../../../../main/webapp/app/entities/u-ser-type/user-type.service';
import { USER_TYPE } from '../../../../../../main/webapp/app/entities/u-ser-type/user-type.model';

describe('Component Tests', () => {

    describe('USER_TYPE Management Detail Component', () => {
        let comp: USER_TYPEDetailComponent;
        let fixture: ComponentFixture<USER_TYPEDetailComponent>;
        let service: USER_TYPEService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [USER_TYPEDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    USER_TYPEService,
                    JhiEventManager
                ]
            }).overrideTemplate(USER_TYPEDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(USER_TYPEDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(USER_TYPEService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new USER_TYPE(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.uSER_TYPE).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
