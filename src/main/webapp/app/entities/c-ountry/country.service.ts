import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { COUNTRY } from './country.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class COUNTRYService {

    private resourceUrl = 'api/c-ountries';

    constructor(private http: Http) { }

    create(cOUNTRY: COUNTRY): Observable<COUNTRY> {
        const copy = this.convert(cOUNTRY);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cOUNTRY: COUNTRY): Observable<COUNTRY> {
        const copy = this.convert(cOUNTRY);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<COUNTRY> {
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

    private convert(cOUNTRY: COUNTRY): COUNTRY {
        const copy: COUNTRY = Object.assign({}, cOUNTRY);
        return copy;
    }
}
