
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar sala</title>
    <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
    <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
</head>

<body>
<div class="container-fluid">
    <div class="col align-middle">
        <div class="card position-absolute top-50 start-50 translate-middle" style="width: 20%">
            <div class="card-header bg-primary text-white text-center"><h5>ACTUALIZAR SALA</h5></div>
            <div class="card-body">
                <form id="sala-form" class="needs-validation" novalidate action="/sala/update" method="post">
                    <input hidden value="${sala.id_sala}" name="id_sala">
                    <div class="form-group mb-3">
                        <label for="nombre" class="fw-bold">Nombre de la sala:</label>
                        <input type="text" name="nombre" id="nombre" class="form-control" placeholder="Sala 1" required maxlength="30" value="${sala.nombre}"/>
                        <div class="invalid-feedback">Campo obligatorio</div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="capacidad" class="fw-bold">Capacidad:</label>
                                <input type="number" name="capacidad" id="capacidad" class="form-control" placeholder="Número personas" required  min="1" max="100" value="${sala.capacidad}"/>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col">
                                <label for="descripcion" class="fw-bold">Descripción:</label>
                                <textarea name="descripcion" id="descripcion" class="form-control" required rows="3" maxlength="200" style="resize: none;" >${sala.descripcion}</textarea>
                                <div class="invalid-feedback">Campo obligatorio</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mb-3">
                        <div class="row">
                            <div class="col text-center">
                                <a href="/sala/salas" class="btn btn-outline-danger mr-2">
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
