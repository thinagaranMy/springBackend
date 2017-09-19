import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { STATE } from './state.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class STATEService {

    private resourceUrl = 'api/s-tates';

    constructor(private http: Http) { }

    create(sTATE: STATE): Observable<STATE> {
        const copy = this.convert(sTATE);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(sTATE: STATE): Observable<STATE> {
        const copy = this.convert(sTATE);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<STATE> {
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

    private convert(sTATE: STATE): STATE {
        const copy: STATE = Object.assign({}, sTATE);
        return copy;
    }
}
