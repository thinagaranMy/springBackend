import { BaseEntity } from './../../shared';

export class STATION implements BaseEntity {
    constructor(
        public id?: number,
        public cOde?: string,
        public dEscription?: string,
    ) {
    }
}
