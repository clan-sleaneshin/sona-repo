import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISworker } from 'app/shared/model/sworker.model';

type EntityResponseType = HttpResponse<ISworker>;
type EntityArrayResponseType = HttpResponse<ISworker[]>;

@Injectable({ providedIn: 'root' })
export class SworkerService {
  public resourceUrl = SERVER_API_URL + 'api/sworkers';

  constructor(protected http: HttpClient) {}

  create(sworker: ISworker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sworker);
    return this.http
      .post<ISworker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sworker: ISworker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sworker);
    return this.http
      .put<ISworker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISworker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISworker[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sworker: ISworker): ISworker {
    const copy: ISworker = Object.assign({}, sworker, {
      birthDate: sworker.birthDate != null && sworker.birthDate.isValid() ? sworker.birthDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sworker: ISworker) => {
        sworker.birthDate = sworker.birthDate != null ? moment(sworker.birthDate) : null;
      });
    }
    return res;
  }
}
