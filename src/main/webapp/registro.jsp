<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:choose>
        <c:when test="${rol == 1}">
            <title>Registrar usuario</title>
            <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">

        </c:when>
        <c:otherwise>
            <title>Registrarse</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Indexstyle.css">
        </c:otherwise>
    </c:choose>
    <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
</head>
<body id="index">
<div class="container">
    <div class="row">
        <div class="col align-middle">
            <div class="card position-absolute top-50 start-50 translate-middle" style="width: 50%;">
                <div class="card-header text-white text-center" style="background-color: #045c4a;">
                    <h5>Registrarse</h5>
                </div>
                <div class="card-body">
                    <form class="row g-3 needs-validation" id="register-form" novalidate action="/api/user/save" method="post">
                        <div class="col-md-4">
                            <label for="validationCustom01" class="fw-bold">Nombre(s):</label>
                            <input type="text" class="form-control" id="validationCustom01" name="nombre" maxlength="40" required>
                            <div class="invalid-feedback">
                                Campo obligatorio
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="validationCustom03" class="fw-bold">Apellido paterno:</label>
                            <input type="text" class="form-control" id="validationCustom03" name="apellido_paterno" maxlength="40" required>
                            <div class="valid-feedback">
                                Campo obligatorio
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="validationCustom02" class="fw-bold">Apellido materno:</label>
                            <input type="text" class="form-control" id="validationCustom02" name="apellido_materno" maxlength="40" required>
                            <div class="invalid-feedback">
                                Campo obligatorio
                            </div>
                        </div>
                        <div class="col-md-7">
                            <label for="validationCustom04" class="fw-bold">Correo electrónico:</label>
                            <input type="email" class="form-control" id="validationCustom04" placeholder="example@utez.edu.com" name="correo" maxlength="40" required>
                            <div class="invalid-feedback">
                                Verifique la extensión del correo
                            </div>
                        </div>
                        <div class="col-md-5">
                            <label for="validationCustom05" class="fw-bold">Contraseña:</label>
                            <input type="password" class="form-control" id="validationCustom05" name="contrasenia" minlength="8" maxlength="40" required>
                            <div class="invalid-feedback">
                                La Contraseña debe tener al menos 8 caracteres
                            </div>
                        </div>
                        <div class="col-md-5">
                            <label for="validationCustom06" class="fw-bold">Teléfono:</label>
                            <input type="text" class="form-control" id="validationCustom06" name="telefono" maxlength="13" required>
                            <div class="invalid-feedback">
                                Campo obligatorio
                            </div>
                        </div>
                        <div class="col-md-2">
                        </div>
                        <!--Botones de opciones-->
                        <label for="rol">Seleccione su rol:</label>
                        <select id="rol" name="rol" required>
                            <option value="" disabled selected>Opciones...</option>
                            <c:if test="${rol == 1}"><option value="">Bibliotecario</option></c:if>
                            <option value="alumno">Alumno</option>
                            <option value="docente">Docente</option>
                        </select>
                        <div class="invalid-feedback">
                            Por favor seleccione su rol
                        </div>
                        <!--Opciones de botones para registro-->
                        <div class="col col-md-12 align-middle">
                            <div class="row" id="campos-extra"></div>
                        </div>
                        <div class="col-md-12 mt-2 text-end">
                            <button id="enviar" type="submit" class="btn btn-success">Continuar</button>
                            <c:choose>
                                <c:when test="${rol == 1}">
                                    <a href="/api/usuario/usuarios" class="btn btn-outline-danger">
                                        Cancelar
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/api/login" class="btn btn-outline-danger">
                                        Cancelar
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="${pageContext.request.contextPath}/layouts/footer.jsp"/>
<script>
    window.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("register-form");
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
                btn.innerHTML = 'Enviar';
            }
            form.classList.add("was-validated");
        }, false);

        const rolSelect = document.getElementById('rol');
        const camposExtraContainer = document.getElementById('campos-extra');

        rolSelect.addEventListener('change', () => {
            const selectedRol = rolSelect.value;
            camposExtraContainer.innerHTML = '';

            if (selectedRol === 'alumno') {
                camposExtraContainer.innerHTML = `
            <!--campos para alumno-->
                                    <div class="card card-body alumno">
                                            <div class="col-12">
                                                <label for="validationCustom07" class="fw-bold">Matrícula:</label>
                                                <input type="text" class="form-control" id="validationCustom07" name="matricula" maxlength="20" required>
                                                <div class="invalid-feedback">
                                                    Campo obligatorio
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <label for="validationCustom08" class="fw-bold">Carrera:</label>
                                                <input type="text" class="form-control" id="validationCustom07" name="carrera" maxlength="200" required>
                                                <div class="invalid-feedback">
                                                    Campo obligatorio
                                                </div>
                                            </div>
                                            <br/>
                                            <div class="col-md-12">
                                                <label for="grado">Seleccione su grado:</label>
                                                <select id="grado" name="grado" required>
                                                    <option value="" disabled selected>Opciones...</option>
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                    <option value="6">6</option>
                                                    <option value="7">7</option>
                                                    <option value="8">8</option>
                                                    <option value="9">9</option>
                                                    <option value="10">10</option>
                                                    <option value="11">11</option>
                                                </select>
                                                    <div class="invalid-feedback">
                                                    Campo obligatorio
                                                    </div>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="validationCustom09" class="fw-bold">Grupo:</label>
                                                <input type="text" class="form-control" id="validationCustom09" name="grupo" maxlength="1" required>
                                                <div class="invalid-feedback">
                                                    Campo obligatorio
                                                </div>
                                            </div>
                                    </div>
        `;
            } else if (selectedRol === 'docente') {
                camposExtraContainer.innerHTML = `
            <!--Formulario docente-->
                                    <div class="card card-body docente">
                                            <div class="col-12">
                                                <label for="validationCustom10" class="fw-bold">Número de trabajador:</label>
                                                <input type="text" class="form-control" id="validationCustom10" name="no_trabajador" maxlength="20" required>
                                                <div class="invalid-feedback">
                                                    Campo obligatorio
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <label for="validationCustom11" class="fw-bold">División academica:</label>
                                                <input type="text" class="form-control" id="validationCustom11" name="division" maxlength="30" required>
                                                <div class="invalid-feedback">
                                                    Campo obligatorio
                                                </div>
                                            </div>
                                    </div>
        `;
            }
        });

        const urlParams = new URLSearchParams(window.location.search);
        const result = urlParams.get('result');
        const mensaje = urlParams.get('message');
        if (result === 'false') {
            Swal.fire('¡Error!', mensaje, 'error');
        }
    }, false);
</script>
</body>
</html>