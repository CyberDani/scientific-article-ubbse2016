package backend.repository.jdbc;

import backend.repository.DAOFactory;
import backend.repository.PDFDAO;

public class JdbcDAOFactory extends DAOFactory {

	@Override
	public PDFDAO getPDFDAO() {
		return new JdbcPDFDAO();
	}
	
}
