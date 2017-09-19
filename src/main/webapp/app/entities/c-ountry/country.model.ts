import { BaseEntity } from './../../shared';

export class COUNTRY implements BaseEntity {
    constructor(
        public id?: number,
        public cOde?: string,
        public dEscription?: string,
    ) {
    }
}
