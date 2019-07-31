import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ISworker, Sworker } from 'app/shared/model/sworker.model';
import { SworkerService } from './sworker.service';

@Component({
  selector: 'jhi-sworker-update',
  templateUrl: './sworker-update.component.html'
})
export class SworkerUpdateComponent implements OnInit {
  sworker: ISworker;
  isSaving: boolean;
  birthDateDp: any;

  ethnicities: ['Asian', 'Black', 'Latin', 'White'];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    nationality: [],
    birthDate: [],
    height: [],
    hairColor: [],
    eyeColor: [],
    ethnicity: []
  });

  constructor(protected sworkerService: SworkerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) { }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sworker }) => {
      this.updateForm(sworker);
      this.sworker = sworker;
    });
  }

  updateForm(sworker: ISworker) {
    this.editForm.patchValue({
      id: sworker.id,
      name: sworker.name,
      nationality: sworker.nationality,
      birthDate: sworker.birthDate,
      height: sworker.height,
      hairColor: sworker.hairColor,
      eyeColor: sworker.eyeColor,
      ethnicity: sworker.ethnicity
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sworker = this.createFromForm();
    if (sworker.id !== undefined) {
      this.subscribeToSaveResponse(this.sworkerService.update(sworker));
    } else {
      this.subscribeToSaveResponse(this.sworkerService.create(sworker));
    }
  }

  private createFromForm(): ISworker {
    const entity = {
      ...new Sworker(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      nationality: this.editForm.get(['nationality']).value,
      birthDate: this.editForm.get(['birthDate']).value,
      height: this.editForm.get(['height']).value,
      hairColor: this.editForm.get(['hairColor']).value,
      eyeColor: this.editForm.get(['eyeColor']).value,
      ethnicity: this.editForm.get(['ethnicity']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISworker>>) {
    result.subscribe((res: HttpResponse<ISworker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option === selectedVals[i]) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
