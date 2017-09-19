import { BaseEntity } from './../../shared';

export class PHY_SITE implements BaseEntity {
    constructor(
        public id?: number,
        public aDdressLine1?: string,
        public aDdressLine2?: string,
        public aDdressLine3?: string,
        public pOstcode?: string,
    ) {
    }
}
