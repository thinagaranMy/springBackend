import { BaseEntity } from './../../shared';

export class STATE implements BaseEntity {
    constructor(
        public id?: number,
        public cOde?: string,
        public dEscription?: string,
    ) {
    }
}
