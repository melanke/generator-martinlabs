package <%= props.daoPackage %>;

import br.com.martinlabs.commons.DaoWrapper;
import br.com.martinlabs.commons.LanguageHolder;
import java.sql.Connection;

/**
 * Responsible for database login operations
 * @author martinlabs CRUD generator
 */
public class LoginServiceDao extends DaoWrapper{
    
    public LoginServiceDao(Connection con, LanguageHolder lang) {
        super(con, lang);
    }
    
    public long getIdOf<%= table.className %>(String account, String password) {
        return nullToZero(selectFirstLong(""
                + "SELECT <%= table.primaryColumns[0].column_name %> "
                + "FROM <%= table.name %> "
                + "WHERE <%= table.accountColumn.column_name %> = ? "
                + "AND <%= table.passwordColumn.column_name %> = sha2(?, 256) ", 
                account, password));
    }
}
