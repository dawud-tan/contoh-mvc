package kemasanku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.mvc.RedirectScoped;

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
