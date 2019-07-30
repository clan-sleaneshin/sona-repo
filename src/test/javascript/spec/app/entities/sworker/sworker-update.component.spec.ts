/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BugtrackerTestModule } from '../../../test.module';
import { SworkerUpdateComponent } from 'app/entities/sworker/sworker-update.component';
import { SworkerService } from 'app/entities/sworker/sworker.service';
import { Sworker } from 'app/shared/model/sworker.model';

describe('Component Tests', () => {
  describe('Sworker Management Update Component', () => {
    let comp: SworkerUpdateComponent;
    let fixture: ComponentFixture<SworkerUpdateComponent>;
    let service: SworkerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BugtrackerTestModule],
        declarations: [SworkerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SworkerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SworkerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SworkerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sworker(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sworker();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
