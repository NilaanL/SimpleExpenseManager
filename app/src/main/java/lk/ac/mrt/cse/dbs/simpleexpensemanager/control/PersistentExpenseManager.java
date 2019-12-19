package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.EmbeddedAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.EmbeddedTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Nilaan on 19.12.2019.
 */

public class PersistentExpenseManager extends ExpenseManager{


    Context context;
    public PersistentExpenseManager(Context ctx)  {
        context=ctx;
        try {

            setup();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }
    @Override

    public void setup() throws ExpenseManagerException {

        SQLiteDatabase db = context.openOrCreateDatabase("170405L", context.MODE_PRIVATE, null);

        // create the databases.
        db.execSQL("CREATE TABLE IF NOT EXISTS Account(" +
                "accountNo VARCHAR PRIMARY KEY," +
                "bankName VARCHAR," +
                "accountHolderName VARCHAR," +
                "balance REAL" +
                " );");


        db.execSQL("CREATE TABLE IF NOT EXISTS Account_Transaction(" +
                "Transaction_id INTEGER PRIMARY KEY," +
                "accountNo VARCHAR," +
                "expenseType INT," +
                "amount REAL," +
                "date DATE," +
                "FOREIGN KEY (accountNo) REFERENCES Account(accountNo)" +
                ");");




        EmbeddedAccountDAO accountDAO = new EmbeddedAccountDAO(db);

        setAccountsDAO(accountDAO);

        setTransactionsDAO(new EmbeddedTransactionDAO(db));
        // dummy data
        Account Acct1 = new Account("00001K", "BOC", "Nilaan L", 10000.0);
        Account Acct2 = new Account("00011I", "HSBC", "Nilaan L", 300000.0);
        getAccountsDAO().addAccount(Acct1);
        getAccountsDAO().addAccount(Acct2);

        /*** End ***/
    }
}
