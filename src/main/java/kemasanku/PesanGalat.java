package kemasanku;

import jakarta.inject.Named;
import jakarta.mvc.RedirectScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RedirectScoped
public class PesanGalat implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> daftarPesanGalat = new ArrayList<>();

	public List<String> getDaftarPesanGalat() {
		return daftarPesanGalat;
	}

	public void setDaftarPesanGalat(List<String> daftarPesanGalat) {
		this.daftarPesanGalat = daftarPesanGalat;
	}

	public void tambahPesanGalat(String pesan) {
		daftarPesanGalat.add(pesan);
	}
}