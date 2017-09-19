import { BaseEntity } from './../../shared';

export class BUS implements BaseEntity {
    constructor(
        public id?: number,
        public cApacity?: string,
        public rEgistrationNumber?: string,
    ) {
    }
}
