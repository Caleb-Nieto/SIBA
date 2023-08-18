<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Registrar Libro</title>
  <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
  <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
</head>

<style>
  .form-group-divider {
    border-top: 2px solid rgb(0, 148, 117);
    margin-top: 20px;
    padding-top: 20px;
  }
</style>
<body>
<div class="container-fluid">
  <div class="col align-middle">
    <div class="card position-absolute top-50 start-50 translate-middle" style="width: 50%;">
      <div class="card-header text-white text-center" style="background: #045c4a"><h5>Registro Libro</h5></div>
      <div class="card-body">
        <form id="libro-form" class="needs-validation" novalidate action="/api/libro/save" method="post" enctype="multipart/form-data">
          <div class="form-group mb-3">
            <div class="row">
              <div class="col">
                <label for="titulo" class="fw-bold">Título:</label>
                <input type="text" name="titulo" id="titulo" class="form-control" required maxlength="50"/>
                <div class="invalid-feedback">Campo obligatorio</div>
              </div>
              <div class="col">
                <label for="autores_ids" class="fw-bold">Autor(es):</label>
                <select name="autores_ids" id="autores_ids" required class="form-select" multiple>
                  <option value="" selected disabled>Seleccione...</option>
                  <c:forEach var="autor" items="${autores}">
                    <h1><c:out value="${autor.nombre}"/></h1>
                    <option value="${autor.id_autor}"><c:out value="${autor.nombre}"/> <c:out
                            value="${autor.apellido_paterno}"/> <c:out
                            value="${autor.apellido_materno}"/></option>
                  </c:forEach>
                </select>
                <div class="invalid-feedback">
                  Por favor seleccione al menos un autor para este libro
                </div>
              </div>
            </div>
          </div>

          <div class="form-group-divider"></div>

          <div class="form-group mb-3">
            <div class="row">
              <div class="col">
                <label for="editorial" class="fw-bold">Editorial:</label>
                <input type="text" name="editorial" id="editorial" class="form-control" required maxlength="30"/>
                <div class="invalid-feedback">Campo obligatorio</div>
              </div>
              <div class="col">
                <label for="isbn" class="fw-bold">ISBN:</label>
                <input type="text" name="isbn" id="isbn" class="form-control" placeholder="" required  maxlength="20"/>
                <div class="invalid-feedback">Campo obligatorio</div>
              </div>
            </div>
          </div>
          <div class="form-group mb-3">
            <div class="row">
              <div class="col">
                <label for="id_ejemplar" class="fw-bold">Id del ejemplar:</label>
                <input type="number" name="id_ejemplar" id="id_ejemplar" class="form-control" required min="0"/>
                <div class="invalid-feedback">Campo obligatorio</div>
              </div>
              <div class="col">
                <label for="observaciones" class="fw-bold">Observaciones:</label>
                <input type="text" name="observaciones" id="observaciones" class="form-control" required  maxlength="20"/>
                <div class="invalid-feedback">Campo obligatorio</div>
              </div>
              <div class="col">
                <label for="ubicacion_id" class="fw-bold">Ubicación:</label>
                <select name="ubicacion_id" id="ubicacion_id" required class="form-select">
                  <option value="" selected disabled>Seleccione...</option>
                  <c:forEach var="ubicacion" items="${ubicaciones}">
                    <option value="${ubicacion.id}"><c:out value="${ubicacion.pasillo}, "/>
                      <c:out value="${ubicacion.seccion}, "/> <c:out
                              value="${ubicacion.estante}"/></option>
                  </c:forEach>
                </select>
                <div class="invalid-feedback">
                  Por favor seleccione una ubicación para este libro
                </div>
              </div>
            </div>
          </div>

          <div class="form-group-divider"></div>

          <div class="row form-row form-group mb-3">
            <div class="col-12">
              <label for="portada" class="fw-bold">Portada del libro</label>
              <input type="file" class="form-control" onchange="handleFileChange()"
                     accept="image/*" id="portada"
                     name="portada" required>
              <div class="invalid-feedback">Campo obligatorio</div>
            </div>
            <div class="card mt-2" id="preview"></div>
          </div>
          <div class="form-group">
            <div class="row">
              <div class="col text-center">
                <a href="/api/libro/libros" class="btn btn-danger mr-2">
                  Cancelar
                </a>
                <button id="enviar" type="submit" class="btn btn-success mr-2 " style="background: rgb(0, 148, 117);">Aceptar</button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<jsp:include page="${pageContext.request.contextPath}/layouts/footer.jsp"/>
<script>

  const handleFileChange = () => {
    const inputFile = document.getElementById("portada").files;
    let preview = document.getElementById("preview");
    preview.innerHTML = "";

    const maxSize = 10 * 1024 * 1024;
    const minHeight = 260;

    for (let i = 0; i < inputFile.length; i++) {
      const file = inputFile[i];
      if (file.size > maxSize) {
        console.log("El archivo excede el tamaño limite de 10 MB:", file.name);
        continue;
      }

      let reader = new FileReader();
      reader.onloadend = (result) => {
        const img = new Image();
        img.onload = () => {
          if (img.height > minHeight) {
            preview.innerHTML += "<img class='card-img-top d-block mx-auto' src='" + result.target.result
                    + "' style='height: 260px;width: auto;'/>";
          } else {
            console.log("Por favor ingresa un formato valido para una portada de libro:", file.name);
          }
        };
        img.src = result.target.result;
      };
      reader.readAsDataURL(file);
    }
  }



  //Codigo que se debe utilizar para validar fomularios de registros o actualizaciones.

  //Codigo para validar un formulario de registro
  const form = document.getElementById("libro-form");
  const btn = document.getElementById("enviar");
  form.addEventListener('submit', event => {
    btn.innerHTML = `<div class="d-flex justify-content-center">
                                <div class="spinner-border" role="status">
                                    <span class="visually-hidden">Cargando...</span>
                                </div>
                            </div>`;
    btn.classList.add("disabled");
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
      btn.classList.remove("disabled");
      btn.innerHTML = 'Aceptar';
    }
    form.classList.add("was-validated");
  }, false);
  //-----------------------------------------


  document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const result = urlParams.get('result');
    const mensaje = urlParams.get('message');
    if (result === 'false') {
      Swal.fire('¡Error!', "Operación no realizada: "+mensaje, 'error')
    }
  });
</script>
</body>
</html>