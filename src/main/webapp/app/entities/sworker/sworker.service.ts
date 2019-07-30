import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISworker } from 'app/shared/model/sworker.model';

type EntityResponseType = HttpResponse<ISworker>;
type EntityArrayResponseType = HttpResponse<ISworker[]>;

@Injectable({
  providedIn: 'root'
})
export class SworkerService {

  public resourceUrl = SERVER_API_URL + 'api/sworkers';

  constructor(protected http: HttpClient) { }

  create(sworker: ISworker): Observable<EntityResponseType> {
    return this.http.post<ISworker>(this.resourceUrl, sworker, { observe: 'response' });
  }

  update(sworker: ISworker): Observable<EntityResponseType> {
    return this.http.put<ISworker>(this.resourceUrl, sworker, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISworker[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISworker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
