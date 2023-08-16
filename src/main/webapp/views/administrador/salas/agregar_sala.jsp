
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Sala</title>
    <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
    <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
</head>
<body>
    <div class="container-fluid">
        <div class="col align-middle">
        <div class="card position-absolute top-50 start-50 translate-middle" style="width: 20%">
            <div class="card-header text-white text-center" style="background: rgb(0, 148, 117)"><h5>REGISTRAR SALA</h5></div>
            <div class="card-body">
                <h1>${sala.nombre}</h1>
                <form id="sala-form" class="needs-validation" novalidate action="/api/sala/save" method="post">
                    <div class="form-group mb-3">
                                <label for="nombre" class="fw-bold">Nombre de la sala:</label>
                                <input type="text" name="nombre" id="nombre"  value="${sala.nombre}" class="form-control" placeholder="Sala #" required maxlength="30"/>
                                <div class="invalid-feedback">Campo obligatorio</div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="capacidad" class="fw-bold">Capacidad:</label>
                                <input type="number" name="capacidad" id="capacidad" value="${sala.capacidad}" class="form-control" placeholder="Número personas" required  min="1" max="100"/>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="descripcion" class="fw-bold">Descripción:</label>
                                <textarea name="descripcion" id="descripcion" value="${sala.descripcion}" class="form-control" required rows="3" maxlength="100" style="resize: none;"></textarea>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col text-center">
                                <a href="/api/sala/salas" class="btn btn-outline-danger mr-2">
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
        //Codigo que se debe utilizar para validar fomularios de registros o actualizaciones.

        //Codigo para validar un formulario de registro
            const form = document.getElementById("sala-form");
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
                    btn.innerHTML = 'AGREGAR SALA';
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