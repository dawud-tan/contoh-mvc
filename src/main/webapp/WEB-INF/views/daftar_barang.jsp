<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="id">
   <head>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <title>Selamat Datang!</title>
   </head>
   <body>
	  <h1>Selamat Datang, ${pengguna}!</h1>
	  <div>
		 <form action="barang" method="post">
			<label for="namaBarang">Nama Barang: </label>
			<input type="text" id="namaBarang" name="namaBarang"
			   autofocus value="${nilaiSebelumnya}">
			<input type="submit" value="Tambah ke Keranjang">
			<input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}" />
		 </form>
		 <form action="hapus-sesi" method="post">
			<input type="submit" value="Hapus Sesi">
		 </form>
	  </div>
	  <c:if test="${not empty pesanGalat}">
		  <c:forEach var="g" items="${pesanGalat.daftarPesanGalat}">
			  <div style="background: rgb(236, 5, 5); color: rgb(255, 255, 255); padding: 10px; text-align: center; border: 1px solid rgb(255, 248, 248); font-weight: bolder; position: relative; margin-bottom: 1rem; border-radius: .25rem; box-sizing: border-box; font-size: 1rem; line-height: 1.5;">
			  ${g}
			  </div>
		  </c:forEach>
	  </c:if>
	  <p>
		 Daftar barang di keranjang anda :<br />
		 <c:forEach var="barang" items="${daftarBarang}">
			${barang}<br />
		 </c:forEach>
	  </p>
   </body>
</html>