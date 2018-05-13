export class Celebrity {
  celebrityId: string;
  photoLocation: string;
  locationOfBirth: string;
  biography: string;
  yearOfBirth: string;
  poster: string;
  isDirector: boolean;
  name: string;
  profileImage: string;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
