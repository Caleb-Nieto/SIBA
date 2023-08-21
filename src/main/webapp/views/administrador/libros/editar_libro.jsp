<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Editar Libro</title>
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
        <div class="card position-absolute top-50 start-50 translate-middle" style="width: 60%;">
            <div class="card-header text-white text-center bg-primary"><h5>ACTUALIZAR LIBRO</h5></div>
            <div class="card-body">
                <form id="libro-form" class="needs-validation" novalidate action="/api/libro/update" method="post" enctype="multipart/form-data">
                    <label for="id_libro" ></label>
                    <input hidden value="${libro.id}" id="id_libro" name="id_libro" />
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="titulo" class="fw-bold">Título:</label>
                                <input type="text" name="titulo" id="titulo" class="form-control" required maxlength="50" value="${libro.titulo}" />
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                            <div class="col">
                                <label for="titulo" class="fw-bold"></label>
                                <div class="dropdown">
                                    <button class="btn btn-light btn-sm dropdown-toggle col-12" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Autor(es)
                                    </button>
                                    <ul class="dropdown-menu">
                                        <c:forEach items="${libro.autores}" var="autor">
                                            <li><c:out value="${autor.nombre} ${autor.apellido_paterno} ${autor.apellido_materno}"/></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group-divider"></div>

                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="editorial" class="fw-bold">Editorial:</label>
                                <input type="text" name="editorial" id="editorial" class="form-control" required maxlength="30" value="${libro.editorial}" />
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                            <div class="col">
                                <label for="isbn" class="fw-bold">ISBN:</label>
                                <input type="text" name="isbn" id="isbn" class="form-control" maxlength="20" disabled value="${libro.isbn}" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="ubicacion_id" class="fw-bold">Ubicación actual:</label>
                                <label><c:out value="Pasillo ${libro.ubicacion.pasillo}, sección ${libro.ubicacion.seccion}, estante ${libro.ubicacion.estante}"/></label>
                                <select name="ubicacion_id" id="ubicacion_id" class="form-select">
                                    <option value="${libro.ubicacion.id}" selected>Seleccione una nueva...</option>
                                    <c:forEach var="ubicacion" items="${ubicaciones}">
                                        <option value="${ubicacion.id}"><c:out value="${ubicacion.pasillo}, "/>
                                            <c:out value="${ubicacion.seccion}, "/> <c:out
                                                    value="${ubicacion.estante}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group-divider"></div>

                    <div class="row form-row form-group mb-3">
                        <div class="col-12">
                            <label for="portada" class="fw-bold">Portada del libro</label>
                            <input type="file" class="form-control"  onchange="handleFileChange()"
                                   accept="image/*" id="portada"
                                   name="portada" />
                        </div>
                        <div class="col-12">
                            <img class="card-img-top d-block mx-auto" style="height: 260px;width: auto;" src="/api/libro/loadfile?file=${libro.id}"/>
                        </div>
                        <div class="card mt-2" id="preview"></div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col text-center">
                                <a href="/api/libro/libros" class="btn btn-danger mr-2">
                                    Cancelar
                                </a>
                                <button id="enviar" type="submit" class="btn btn-success mr-2 bg-primary">Actualizar</button>
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

        const maxSize = 10 * 1024 * 1024; // 10 MB in bytes
        const minHeight = 260; // Minimum height in pixels

        for (let i = 0; i < inputFile.length; i++) {
            const file = inputFile[i];
            if (file.size > maxSize) {
                console.log("El archivo excede el tamaño limite de 10 MB:", file.name);
                continue; // Skip this file
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
            btn.innerHTML = 'Actualizar';
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