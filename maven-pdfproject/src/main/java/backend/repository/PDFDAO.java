package backend.repository;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import backend.model.PDF;

public interface PDFDAO {
	
	List<PDF> getAllPDFs();

	void insertPDF(String collection, PDF pdf) throws JsonProcessingException;
}
