import { BaseEntity } from './../../shared';

export class ROUTES implements BaseEntity {
    constructor(
        public id?: number,
        public cOde?: string,
        public dEscription?: string,
    ) {
    }
}
