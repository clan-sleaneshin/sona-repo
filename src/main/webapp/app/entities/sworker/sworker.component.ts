import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ISworker } from 'app/shared/model/sworker.model';

import { AccountService } from 'app/core';
import { SworkerService } from './sworker.service';

@Component({
  selector: 'jhi-sworker',
  templateUrl: './sworker.component.html'
})
export class SworkerComponent implements OnInit, OnDestroy {
  currentAccount: any;
  sworkers: ISworker[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  reverse: any;

  constructor(protected sworkerService: SworkerService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected eventManager: JhiEventManager) { }

  loadAll() {
    this.sworkerService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISworker[]>) => this.paginateSworkers(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  registerChangeInSworkers() {
    this.eventSubscriber = this.eventManager.subscribe('sworkersListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSworkers(data: ISworker[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.sworkers = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSworkers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

}
