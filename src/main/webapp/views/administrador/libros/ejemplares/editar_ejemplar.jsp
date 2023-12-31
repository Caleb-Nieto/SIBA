<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Actualizar ejemplar</title>
    <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
    <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
</head>
<body>
<div class="container-fluid">
    <div class="col align-middle">
        <div class="card position-absolute top-50 start-50 translate-middle" style="width: 20%">
            <div class="card-header text-white bg-primary text-center"><h5>Actualizar ejemplar</h5></div>
            <div class="card-body">
                <form id="ejemplar-form" class="needs-validation" novalidate action="/api/ejemplar/update" method="post">
                    <input value="${ejemplar.libro.id}" name="id_libro" hidden>
                    <input value="${ejemplar.id_ejemplar}" name="id_ejemplar" hidden>
                    <div class="form-group mb-3">
                        <label for="ejemplar" class="fw-bold">Identificador de ejemplar:</label>
                        <input type="number" name="ejemplar" value="${ejemplar.ejemplar}" id="ejemplar" class="form-control" required min="0"/>
                        <div class="invalid-feedback">Campo obligatorio</div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="ap" class="fw-bold">Observaciones:</label>
                                <input type="text" name="observaciones" value="${ejemplar.observaciones}" id="ap" class="form-control" required maxlength="40"/>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col text-center">
                                <a href="/api/ejemplar/ejemplares?id_libro=${ejemplar.libro.id}" class="btn btn-outline-danger mr-2">
                                    Cancelar
                                </a>
                                <button id="enviar" type="submit" class="btn btn-success mr-2  bg-primary">Actualizar</button>
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
