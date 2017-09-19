/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PHY_SITEDetailComponent } from '../../../../../../main/webapp/app/entities/p-hy-site/phy-site-detail.component';
import { PHY_SITEService } from '../../../../../../main/webapp/app/entities/p-hy-site/phy-site.service';
import { PHY_SITE } from '../../../../../../main/webapp/app/entities/p-hy-site/phy-site.model';

describe('Component Tests', () => {

    describe('PHY_SITE Management Detail Component', () => {
        let comp: PHY_SITEDetailComponent;
        let fixture: ComponentFixture<PHY_SITEDetailComponent>;
        let service: PHY_SITEService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [PHY_SITEDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PHY_SITEService,
                    JhiEventManager
                ]
            }).overrideTemplate(PHY_SITEDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PHY_SITEDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PHY_SITEService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PHY_SITE(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pHY_SITE).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
