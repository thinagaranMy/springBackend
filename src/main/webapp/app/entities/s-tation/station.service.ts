import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { STATION } from './station.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class STATIONService {

    private resourceUrl = 'api/s-tations';

    constructor(private http: Http) { }

    create(sTATION: STATION): Observable<STATION> {
        const copy = this.convert(sTATION);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(sTATION: STATION): Observable<STATION> {
        const copy = this.convert(sTATION);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<STATION> {
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

    private convert(sTATION: STATION): STATION {
        const copy: STATION = Object.assign({}, sTATION);
        return copy;
    }
}
