import {Transaction} from './transaction.model';

export class Wallet {
  public name: string;
  public balance: bigint;
  public id: bigint;
  public transactions: Array<Transaction>;
}
