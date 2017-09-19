/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { COUNTRYDetailComponent } from '../../../../../../main/webapp/app/entities/c-ountry/country-detail.component';
import { COUNTRYService } from '../../../../../../main/webapp/app/entities/c-ountry/country.service';
import { COUNTRY } from '../../../../../../main/webapp/app/entities/c-ountry/country.model';

describe('Component Tests', () => {

    describe('COUNTRY Management Detail Component', () => {
        let comp: COUNTRYDetailComponent;
        let fixture: ComponentFixture<COUNTRYDetailComponent>;
        let service: COUNTRYService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [COUNTRYDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    COUNTRYService,
                    JhiEventManager
                ]
            }).overrideTemplate(COUNTRYDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(COUNTRYDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(COUNTRYService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new COUNTRY(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cOUNTRY).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
