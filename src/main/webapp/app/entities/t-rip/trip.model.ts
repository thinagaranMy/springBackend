import { BaseEntity } from './../../shared';

export class TRIP implements BaseEntity {
    constructor(
        public id?: number,
        public sCheduledtime?: string,
    ) {
    }
}
