package com.example.AllTimeExtreme.servis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.AllTimeExtreme.nastavenia.NastaveniaXML;
import com.example.AllTimeExtreme.repository.AllTimeRepo;
import com.example.AllTimeExtreme.repository.CennyPapierRepo;

@Service
public class AllTimeServis {

	@Autowired
	AllTimeRepo allRepo;
	
	@Autowired
	CennyPapierRepo cpRepo;
	
	@Bean
	public void najdiAllTimeHigh() {
//		NastaveniaXML nastavenia = NastaveniaXML.getInstance();
//		all_time_high je subquery
//		zoradujem podla subquery
//		Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "all_time_high");
		
//		for(Integer modulo=0; modulo<32; modulo++) {
//			System.out.println("spracuvam modulo: "+modulo);
//			allRepo.saveAll(allRepo.vratVsetkyExtremy(modulo, "all_time_high heloo DESCa"));
//			allRepo.vratVsetkyExtremy(modulo, 4775, "graf.ramec_4_idcpm_"+modulo, pageable).forEach(System.out::println);
//		}	
		
		for(Integer modulo=0; modulo<32; modulo++) {
			System.out.println("spracuvam modulo: "+modulo);
			allRepo.saveAll(allRepo.vratVsetkyExtremy(modulo));
		}
	}
}
