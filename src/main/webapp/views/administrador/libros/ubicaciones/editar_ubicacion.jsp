<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Actualizar Ubicacion</title>
    <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
    <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
</head>
<body>
<div class="container-fluid">
    <div class="col align-middle">
        <div class="card position-absolute top-50 start-50 translate-middle" style="width: 20%">
            <div class="card-header text-white text-center bg-primary"><h5>ACTUALIZAR UBICACIÓN</h5></div>
            <div class="card-body">
                <form id="ubi-form" class="needs-validation" novalidate action="/api/ubicacion/update" method="post">
                    <div class="form-group mb-3">
                        <input hidden value="${ubicacion.id}" name="id"/>
                        <label for="pasillo" class="fw-bold">Pasillo:</label>
                        <input type="number" name="pasillo" id="pasillo" class="form-control" value="${ubicacion.pasillo}" required min="0"/>
                        <div class="invalid-feedback">Campo obligatorio</div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="seccion" class="fw-bold">Sección:</label>
                                <input type="number" name="seccion" id="seccion" class="form-control" value="${ubicacion.seccion}" required min="0"/>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="estante" class="fw-bold">Estante:</label>
                                <input type="text" name="estante" id="estante" class="form-control" value="${ubicacion.estante}" required maxlength="4"/>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col text-center">
                                <a href="/api/ubicacion/ubicaciones" class="btn btn-outline-danger mr-2">
                                    Cancelar
                                </a>
                                <button id="enviar" type="submit" class="btn btn-primary mr-2">Actualizar</button>
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
    const form = document.getElementById("ubi-form");
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


    /*Mensaje de la libreria sweetalert que se despliega cuando en la url el parametro message tiene un valor.
    Despues, en una variable se recoge el mensaje y lo coloca como mensaje en la alerta
     */
    document.addEventListener('DOMContentLoaded', () => {
        const urlParams = new URLSearchParams(window.location.search);
        const result = urlParams.get('result');
        const mensaje = urlParams.get('message');
        if (result === 'false') {
            Swal.fire('¡Error!', "Operación no realizada: "+mensaje, 'error');
        }

    });


</script>
</body>
</html>