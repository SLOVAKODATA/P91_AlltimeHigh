package com.example.AllTimeExtreme.servis;

import com.example.AllTimeExtreme.konstanty.RiadneHodnotyCennehoPapieraKonstanty;
import com.example.AllTimeExtreme.model.All_time_extremy;
import com.example.AllTimeExtreme.nastavenia.NastaveniaXML;
import com.example.AllTimeExtreme.repository.SplitCennehoPapieraRealizaciaRepo;
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

	@Autowired
	SplitCennehoPapieraRealizaciaRepo splityRepo;
	

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

			if (tyzdnovyExtremCennehoPapiera!=null) {
				vyriesHigh(extrem, tyzdnovyExtremCennehoPapiera);
				vyriesLow(extrem, tyzdnovyExtremCennehoPapiera);
			}

		}

		return extremyPredvolenychRamcov;

	}

	private void vyriesHigh(All_time_extremy extrem, All_time_extremy tyzdnovyExtremCennehoPapiera) {
		// high
		Double maxPredvolenehoRamca = extrem.getAll_time_high();
		Double maxTyzdnovehoRamca = tyzdnovyExtremCennehoPapiera.getAll_time_high();

		// ak je dnes nahodou realizovany stock split, tak sa v ten den neustale (kazdym spustenim tohto programu) meraju najnovsie max/min hodnoty
		if (jeDnesKalkulacnyDenStockSplitu(extrem.getId_cenneho_papiera())) {
			System.out.println("Dnesny den sa pre dany ticker ("+ extrem.getId_cenneho_papiera()+") meraju rozsahy High/Low.");
			extrem.setAll_time_high(maxTyzdnovehoRamca);
			return;
		}

		// toto je bezny priprav v dni, kedy nemame stock split
		if ((maxPredvolenehoRamca!=null)&&(maxTyzdnovehoRamca!=null)&&(jeRiadnaHodnota(maxPredvolenehoRamca))) {
			if (maxTyzdnovehoRamca>maxPredvolenehoRamca) {
				extrem.setAll_time_high(maxTyzdnovehoRamca);
			}
		}
	}

	private void vyriesLow(All_time_extremy extrem, All_time_extremy tyzdnovyExtremCennehoPapiera) {
		// low
		Double minPredvolenehoRamca = extrem.getAll_time_low();
		Double minTyzdnovehoRamca = tyzdnovyExtremCennehoPapiera.getAll_time_low();

		// ak je dnes nahodou realizovany stock split, tak sa v ten den neustale (kazdym spustenim tohto programu) meraju najnovsie max/min hodnoty
		if (jeDnesKalkulacnyDenStockSplitu(extrem.getId_cenneho_papiera())) {
			System.out.println("Dnesny den sa pre dany ticker ("+ extrem.getId_cenneho_papiera()+") meraju rozsahy High/Low.");
			extrem.setAll_time_low(minTyzdnovehoRamca);
			return;
		}

		// toto je bezny priprav v dni, kedy nemame stock split
		if ((minPredvolenehoRamca!=null)&&(minTyzdnovehoRamca!=null)&&(jeRiadnaHodnota(minPredvolenehoRamca))) {
			if (minTyzdnovehoRamca<minPredvolenehoRamca) {
				extrem.setAll_time_low(minTyzdnovehoRamca);
			}
		}

		if (!jeRiadnaHodnota(extrem.getAll_time_low())) {
			extrem.setAll_time_low(null);
		}
	}

	private boolean jeDnesKalkulacnyDenStockSplitu(Integer idcp) {
		return (splityRepo.dnesnaRealizaciaStockSplitu(idcp)!=null);
	}

	/**
	 * Kedze trieda All_time_extremy nie je vhodna, na rychly pristup cez kluc, vyriesime to tak, ze ju pretranformujeme na heatmapu
	 * @param extremyTyzdennychRamcov
	 * @return
	 */
	private HashMap<Integer, All_time_extremy> namapujExtremyTyzdnovychRamcovNaHasmapu(List<All_time_extremy> extremyTyzdennychRamcov) {
		HashMap<Integer, All_time_extremy> retVal = new HashMap<Integer, All_time_extremy>();
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
