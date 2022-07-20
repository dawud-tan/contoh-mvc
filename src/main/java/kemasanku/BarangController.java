package kemasanku;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.View;
import javax.mvc.binding.BindingResult;
import javax.mvc.binding.MvcBinding;
import javax.mvc.binding.ParamError;
import javax.mvc.security.CsrfProtected;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SessionScoped
@Controller
@Path("/")
public class BarangController implements Serializable {

    @Inject
    private Models modelnya;
    private List<String> isiKeranjang = new ArrayList<>();

    @Inject
    private BindingResult validasi;

    @Inject
    private PesanGalat pesanGalat;

    @Inject
    private HttpServletRequest request;

    public BarangController() {
        isiKeranjang.add("Laptop");
        isiKeranjang.add("Tetikus Nirkabel");
    }

    @GET
    @Path("barang")
    @Produces(MediaType.TEXT_HTML)
    @View("daftar_barang.jsp")
    public void daftarBarang() {
        modelnya.put("pengguna", "manto");
        modelnya.put("daftarBarang", isiKeranjang);
    }

    @POST
    @CsrfProtected
    @Path("barang")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String tambahBarang(
            @MvcBinding
            @NotEmpty(message = "nama barang tidak boleh dikosongi")
            @Size(min = 2, message = "nama barang minimal 2 karakter")
            @FormParam("namaBarang") String namaBarang) {
        if (adakahPesanGalat()) {
            modelnya.put("pesanGalat", pesanGalat);
            return "redirect:/barang";
        }

        isiKeranjang.add(namaBarang);
        return "redirect:/barang";
    }

    @POST
    @Path("hapus-sesi")
    public Response hapusSesi() {
        HttpSession sesi = request.getSession(false);
        if (sesi != null) {
            sesi.invalidate();
        }
        return Response.seeOther(URI.create("/barang")).build();
    }

    private boolean adakahPesanGalat() {
        if (validasi.isFailed()) {
            validasi.getAllErrors().stream().forEach((ParamError pg) -> {
                pesanGalat.tambahPesanGalat(pg.getMessage());
            });
            return true;
        }
        return false;
    }

}
