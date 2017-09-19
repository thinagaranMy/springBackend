import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CUSTOM_USER } from './custom-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CUSTOM_USERService {

    private resourceUrl = 'api/c-ustom-users';

    constructor(private http: Http) { }

    create(cUSTOM_USER: CUSTOM_USER): Observable<CUSTOM_USER> {
        const copy = this.convert(cUSTOM_USER);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cUSTOM_USER: CUSTOM_USER): Observable<CUSTOM_USER> {
        const copy = this.convert(cUSTOM_USER);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CUSTOM_USER> {
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

    private convert(cUSTOM_USER: CUSTOM_USER): CUSTOM_USER {
        const copy: CUSTOM_USER = Object.assign({}, cUSTOM_USER);
        return copy;
    }
}
