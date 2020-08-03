package com.example.AllTimeExtreme.servis;

import com.example.AllTimeExtreme.konstanty.CasovyRamecKonstanty;
import com.example.AllTimeExtreme.konstanty.RiadneHodnotyCennehoPapieraKonstanty;
import com.example.AllTimeExtreme.model.All_time_extremy;
import com.example.AllTimeExtreme.nastavenia.NastaveniaXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.AllTimeExtreme.repository.AllTimeRepo;
import com.example.AllTimeExtreme.repository.CennyPapierRepo;

import java.util.HashMap;
import java.util.List;

@Service
public class AllTimeServis {

	@Autowired
	AllTimeRepo allRepo;
	
	@Autowired
	CennyPapierRepo cpRepo;
	

	/**
	 * Najde a ulozi All Time High
	 */
	@Bean
	public void najdiAllTimeHigh() {
		NastaveniaXML nastavenia = NastaveniaXML.getInstance();
		for(Integer modulo=0; modulo<32; modulo++) {
			System.out.print("Spracuvam modulo: "+modulo+"... ");
			List<All_time_extremy> extremyPredvolenychRamcov = allRepo.vratVsetkyExtremyHodinovychRamcov(modulo);
			List<All_time_extremy> extremyTyzdennychRamcov = allRepo.vratVsetkyExtremyTyznovychRamcov(modulo);
			System.out.print("spajam... ");
			List<All_time_extremy> extremy = spoj(extremyPredvolenychRamcov, extremyTyzdennychRamcov);
			System.out.print("ukladam... ");
			allRepo.saveAll(extremy);
			System.out.println("[ OK ]");
		}
	}

	/**
	 * Spoji extremy Dennych Ramcov s Vysledkami z tyzdennych ramcov
	 * @param extremyPredvolenychRamcov
	 * @param extremyTyzdennychRamcov
	 * @return
	 */
	private List<All_time_extremy> spoj(List<All_time_extremy> extremyPredvolenychRamcov, List<All_time_extremy> extremyTyzdennychRamcov) {

		// tyzdnove ramce si potrebujem previest do hasmapy
		HashMap<Integer, All_time_extremy> hmExtremy = namapujExtremyTyzdnovychRamcovNaHasmapu(extremyTyzdennychRamcov);

		// pre vsetky cp po potrebujem pozriet, kde je vacsie high alebo low
		for (All_time_extremy extrem: extremyPredvolenychRamcov) {
			Integer idcp = extrem.getId_cenneho_papiera();

			// pozrieme sa, ci dany denny papier nemal extremy uz v minulosti:
			All_time_extremy tyzdnovyExtremCennehoPapiera = hmExtremy.get(extrem.getId_cenneho_papiera());

			// high
			Double maxPredvolenehoRamca = extrem.getAll_time_high();
			Double maxTyzdnovehoRamca = tyzdnovyExtremCennehoPapiera.getAll_time_high();
			if ((maxPredvolenehoRamca!=null)&&(maxTyzdnovehoRamca!=null)&&(jeRiadnaHodnota(maxPredvolenehoRamca))) {
				if (maxTyzdnovehoRamca>maxPredvolenehoRamca) {
					extrem.setAll_time_high(maxTyzdnovehoRamca);
				}
			}

			// low
			Double minPredvolenehoRamca = extrem.getAll_time_low();
			Double minTyzdnovehoRamca = tyzdnovyExtremCennehoPapiera.getAll_time_low();
			if ((minPredvolenehoRamca!=null)&&(minTyzdnovehoRamca!=null)&&(jeRiadnaHodnota(minPredvolenehoRamca))) {
				if (minTyzdnovehoRamca<minPredvolenehoRamca) {
					extrem.setAll_time_low(minTyzdnovehoRamca);
				}
			}
			if (!jeRiadnaHodnota(extrem.getAll_time_low())) {
				extrem.setAll_time_low(null);
			}
		}
		return extremyPredvolenychRamcov;
	}

	/**
	 * Kedze trieda All_time_extremy nie je vhodna, na rychly pristup cez kluc, vyriesime to tak, ze ju pretranformujeme na heatmapu
	 * @param extremyTyzdennychRamcov
	 * @return
	 */
	private HashMap<Integer,All_time_extremy> namapujExtremyTyzdnovychRamcovNaHasmapu(List<All_time_extremy> extremyTyzdennychRamcov) {
		HashMap<Integer,All_time_extremy> retVal = new HashMap<Integer,All_time_extremy>();
		for (All_time_extremy extrem: extremyTyzdennychRamcov) {
			retVal.put(extrem.getId_cenneho_papiera(), extrem);
		}
		return retVal;
	}

	/**
	 * Skontorluje, ci je to vobec hodnota, ktora je mozna pre definicny obor cenneho papiera
	 * @param hodnota
	 * @return
	 */
	private boolean jeRiadnaHodnota(Double hodnota) {
		if (hodnota==null) {
			return false;
		}
		return hodnota > RiadneHodnotyCennehoPapieraKonstanty.NULA_JE_MENSIA_AKO;
	}

}

// stary sposob:
//		NastaveniaXML nastavenia = NastaveniaXML.getInstance();
//		all_time_high je subquery
//		zoradujem podla subquery
//		Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "all_time_high");

//		for(Integer modulo=0; modulo<32; modulo++) {
//			System.out.println("spracuvam modulo: "+modulo);
//			allRepo.saveAll(allRepo.vratVsetkyExtremy(modulo, "all_time_high heloo DESCa"));
//			allRepo.vratVsetkyExtremy(modulo, 4775, "graf.ramec_4_idcpm_"+modulo, pageable).forEach(System.out::println);
//		}
