package kemasanku;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.MvcBinding;
import jakarta.mvc.binding.ParamError;
import jakarta.mvc.security.CsrfProtected;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Controller
@Path("/")
public class BarangController implements Serializable {

	@Inject
	private Models modelnya;
	private List <String> isiKeranjang = new ArrayList<>();

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