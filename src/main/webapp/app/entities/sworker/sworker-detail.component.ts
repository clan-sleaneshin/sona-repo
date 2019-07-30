import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISworker } from 'app/shared/model/sworker.model';

@Component({
  selector: 'jhi-sworker-detail',
  templateUrl: './sworker-detail.component.html'
})
export class SworkerDetailComponent implements OnInit {
  sworker: ISworker;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sworker }) => {
      this.sworker = sworker;
    });
  }

  previousState() {
    window.history.back();
  }
}
