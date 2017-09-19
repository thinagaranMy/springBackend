import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { INCIDENT_REPORT } from './incident-report.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class INCIDENT_REPORTService {

    private resourceUrl = 'api/i-ncident-reports';

    constructor(private http: Http) { }

    create(iNCIDENT_REPORT: INCIDENT_REPORT): Observable<INCIDENT_REPORT> {
        const copy = this.convert(iNCIDENT_REPORT);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(iNCIDENT_REPORT: INCIDENT_REPORT): Observable<INCIDENT_REPORT> {
        const copy = this.convert(iNCIDENT_REPORT);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<INCIDENT_REPORT> {
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

    private convert(iNCIDENT_REPORT: INCIDENT_REPORT): INCIDENT_REPORT {
        const copy: INCIDENT_REPORT = Object.assign({}, iNCIDENT_REPORT);
        return copy;
    }
}
