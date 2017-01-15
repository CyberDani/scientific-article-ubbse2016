package backend.repository;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import backend.model.PDF;

/**
 * 
 * PDF Data Access interface
 *
 */
public interface PDFDAO {
	
	List<PDF> getAllPDFs();

	void insertPDF(String collection, PDF pdf) throws JsonProcessingException;
}
