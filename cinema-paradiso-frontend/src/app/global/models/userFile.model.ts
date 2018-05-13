export class UserFile {
  name: string;
  id: number;
  profileImage: string;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
