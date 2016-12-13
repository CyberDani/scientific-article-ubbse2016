package backend.repository;

import java.util.List;

import backend.repository.exception.RepositoryException;
import frontend.model.PDF;

public interface PDFDAO {
	
	List<PDF> getAllPDFs();

}
