package com.example.AllTimeExtreme.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.AllTimeExtreme.model.All_time_extremy;

@Repository
public interface AllTimeRepo extends CrudRepository<All_time_extremy, Integer> {
	
	@Query(value="SELECT ID_CENNEHO_PAPIERA, MAX(HIGH) AS all_time_high, MIN(LOW) AS all_time_low FROM graf.ramec_2_idcpm_:modulo GROUP BY id_cenneho_papiera ", nativeQuery=true)
	public List<All_time_extremy> vratVsetkyExtremy(Integer modulo);
	
	
	//	public List<All_time_extremy> vratVsetkyExtremy(Integer modulo, Integer idcp, String tabulka,
//			Pageable pageable);
	

}
