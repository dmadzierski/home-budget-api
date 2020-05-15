export class ApiUri {
  public static register: string = ApiUri.getBase() + '/register';
  public static login: string = ApiUri.getBase() + '/login';
  public static resource: string = ApiUri.getBase() + '/resource';
  public static user: string = ApiUri.getBase() + '/user';
  public static userWallets: string = ApiUri.getBase() + '/wallet';
  public static addWallet: string = ApiUri.getBase() + '/wallet/add';

  public static getBase(): string {
    return 'http://localhost:8080';
  }
}
