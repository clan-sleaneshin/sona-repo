import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sworker, ISworker } from 'app/shared/model/sworker.model';
import { SworkerService } from './sworker.service';
import { SworkerComponent } from './sworker.component';
import { SworkerDetailComponent } from './sworker-detail.component';
import { SworkerUpdateComponent } from './sworker-update.component';
import { SworkerDeletePopupComponent } from './sworker-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SworkerResolve implements Resolve<ISworker> {
  constructor(private service: SworkerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISworker> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Sworker>) => response.ok),
        map((project: HttpResponse<Sworker>) => project.body)
      );
    }
    return of(new Sworker());
  }
}

export const sworkerRoute: Routes = [
  {
    path: '',
    component: SworkerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Sxx Workers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SworkerDetailComponent,
    resolve: {
      project: SworkerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sxx Workers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SworkerUpdateComponent,
    resolve: {
      project: SworkerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sxx Workers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SworkerUpdateComponent,
    resolve: {
      project: SworkerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sxx Workers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sworkerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SworkerDeletePopupComponent,
    resolve: {
      project: SworkerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sxx Workers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
