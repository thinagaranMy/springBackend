import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { USER_TYPE } from './user-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class USER_TYPEService {

    private resourceUrl = 'api/u-ser-types';

    constructor(private http: Http) { }

    create(uSER_TYPE: USER_TYPE): Observable<USER_TYPE> {
        const copy = this.convert(uSER_TYPE);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(uSER_TYPE: USER_TYPE): Observable<USER_TYPE> {
        const copy = this.convert(uSER_TYPE);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<USER_TYPE> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(uSER_TYPE: USER_TYPE): USER_TYPE {
        const copy: USER_TYPE = Object.assign({}, uSER_TYPE);
        return copy;
    }
}
