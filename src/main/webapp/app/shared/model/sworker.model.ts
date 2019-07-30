import { Moment } from 'moment';

export interface ISworker {
  id?: number;
  name?: string;
  nationality?: string;
  birthDate?: Moment;
  height?: number;
  hairColor?: string;
  eyeColor?: string;
  ethnicity?: string;
}

export class Sworker implements ISworker {
  constructor(
    public id?: number,
    public name?: string,
    public nationality?: string,
    public birthDate?: Moment,
    public height?: number,
    public hairColor?: string,
    public eyeColor?: string,
    public ethnicity?: string
  ) {}
}
