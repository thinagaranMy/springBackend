import { BaseEntity } from './../../shared';

export class USER_TYPE implements BaseEntity {
    constructor(
        public id?: number,
        public cOde?: string,
        public dEscription?: string,
    ) {
    }
}
