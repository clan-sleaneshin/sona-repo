import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISworker } from 'app/shared/model/sworker.model';
import { SworkerService } from './sworker.service';

@Component({
  selector: 'jhi-sworker-delete-dialog',
  templateUrl: './sworker-delete-dialog.component.html'
})
export class SworkerDeleteDialogComponent {
  sworker: ISworker;

  constructor(protected sworkerService: SworkerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sworkerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sworkerListModification',
        content: 'Deleted an sworker'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sworker-delete-popup',
  template: ''
})
export class SworkerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sworker }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SworkerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sworker = sworker;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sworker', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sworker', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
