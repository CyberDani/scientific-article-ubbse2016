package backend.repository;

import backend.repository.jdbc.JdbcDAOFactory;

public abstract class DAOFactory {

	public static DAOFactory getInstance() {
		return new JdbcDAOFactory();
	}
	
	public abstract PDFDAO getPDFDAO();

}
