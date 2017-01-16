package backend.repository;

import backend.repository.jdbc.JdbcDAOFactory;

/**
 * 
 * Abstract class for Data Access
 *
 */
public abstract class DAOFactory {

	public static DAOFactory getInstance() {
		return new JdbcDAOFactory();
	}
	
	public abstract PDFDAO getPDFDAO();

}
