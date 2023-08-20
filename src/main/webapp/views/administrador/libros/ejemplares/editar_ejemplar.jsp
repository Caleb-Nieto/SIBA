<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Editar Ejemplar</title>
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
            <div class="card-header text-white text-center bg-primary"><h5>ACTUALIZAR EJEMPLAR</h5></div>
            <div class="card-body">
                <form id="ejemplar-form" class="needs-validation" novalidate action="/api/ejemplar/update" method="post" enctype="multipart/form-data">
                    <label for="ejemplar" ></label>
                    <input hidden value="${ejemplar.id}" id="ejemplar" name="ejemplar" />
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="observaciones" class="fw-bold">Título:</label>
                                <input type="text" name="observaciones" id="observaciones" class="form-control" required maxlength="50" value="${ejemplar.observaciones}" />
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        <div class="col-12">
                            <img class="card-img-top d-block mx-auto" style="height: 260px;width: auto;" src="/api/ejemplar/loadfile?file=${ejemplar.ejemplar}"/>
                        </div>
                        <div class="card mt-2" id="preview"></div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col text-center">
                                <a href="/api/ejemplar/ejemplares" class="btn btn-danger mr-2">
                                    Cancelar
                                </a>
                                <button id="enviar" type="submit" class="btn btn-success mr-2 bg-primary">Actualizar</button>
                            </div>
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

    //Codigo que se debe utilizar para validar fomularios de registros o actualizaciones.

    //Codigo para validar un formulario de registro
    const form = document.getElementById("ejemplar-form");
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
