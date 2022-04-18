package kemasanku;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/")
public class BarangController {

	private Map<String, Object> modelnya = new HashMap<>();
	private List <String> isiKeranjang = new ArrayList<>();

	public BarangController() {
		isiKeranjang.add("Laptop Kerja");
		isiKeranjang.add("Tetikus Nirkabel");
	}

	@GET
	@Path("barang")
	@Produces(MediaType.TEXT_HTML)
	public Viewable daftarBarang() {
		modelnya.put("pengguna", "manto");
		modelnya.put("daftarBarang", isiKeranjang);
		return new Viewable("/daftar_barang", modelnya);
	}

	@POST
	@Path("barang")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ErrorTemplate(name = "/daftar_barang")
	public Response tambahBarang(
	    @Valid
	    @NotEmpty(message = "nama barang tidak boleh dikosongi")
		@Size(min = 2, message = "nama barang minimal 2 karakter")
		@FormParam("namaBarang") String namaBarang) {

		isiKeranjang.add(namaBarang);
		return Response.seeOther(URI.create("/barang")).build();
	}

	@POST
	@Path("hapus-sesi")
	public Response hapusSesi(@Context HttpServletRequest request) {
		HttpSession sesi = request.getSession(false);
		if (sesi != null) {
			sesi.invalidate();
		}
		return Response.seeOther(URI.create("/barang")).build();
	}

}