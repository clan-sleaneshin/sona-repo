/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BugtrackerTestModule } from '../../../test.module';
import { SworkerDetailComponent } from 'app/entities/sworker/sworker-detail.component';
import { Sworker } from 'app/shared/model/sworker.model';

describe('Component Tests', () => {
  describe('Sworker Management Detail Component', () => {
    let comp: SworkerDetailComponent;
    let fixture: ComponentFixture<SworkerDetailComponent>;
    const route = ({ data: of({ sworker: new Sworker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BugtrackerTestModule],
        declarations: [SworkerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SworkerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SworkerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sworker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
