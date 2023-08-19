<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Inicio de sesión</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Indexstyle.css">
  <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
</head>
<body id="index">
<main class="form-signin w-100 m-auto">
  <form id="login-form" action="/api/login" class="needs-validation" novalidate method="post" style="margin-top: 100px">
    <div class="col text-center">
      <svg xmlns="http://www.w3.org/2000/svg" width="72" height="67" fill="currentColor" class="bi bi-buildings-fill" viewBox="0 0 16 16">
        <path d="M15 .5a.5.5 0 0 0-.724-.447l-8 4A.5.5 0 0 0 6 4.5v3.14L.342 9.526A.5.5 0 0 0 0 10v5.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V14h1v1.5a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5V.5ZM2 11h1v1H2v-1Zm2 0h1v1H4v-1Zm-1 2v1H2v-1h1Zm1 0h1v1H4v-1Zm9-10v1h-1V3h1ZM8 5h1v1H8V5Zm1 2v1H8V7h1ZM8 9h1v1H8V9Zm2 0h1v1h-1V9Zm-1 2v1H8v-1h1Zm1 0h1v1h-1v-1Zm3-2v1h-1V9h1Zm-1 2h1v1h-1v-1Zm-2-4h1v1h-1V7Zm3 0v1h-1V7h1Zm-2-2v1h-1V5h1Zm1 0h1v1h-1V5Z"/>
      </svg>
    </div> <br>
    <h1 class="h3 mb-3 fw-normal text-center" id="title">SIBA Login</h1>
    <div class="form-floating">
      <input type="email" value="${param.correo}" class="form-control" id="correo" name="correo" pattern=".+@utez\.edu\.mx$" placeholder="name@example.com" size="35" required>
      <label for="correo">Correo electrónico</label>
      <div id="feedbacke" class="invalid-feedback text-start" style="font-size: medium;">Ingresa un correo institucional válido</div>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="contrasenia" name="contrasenia" placeholder="Contraseña" required>
      <label for="contrasenia">Contraseña</label>
      <div id="feedbackp" class="invalid-feedback text-start" style="font-size: medium;">Ingresa tu contraseña</div>
    </div>
    <label class="label small"><a href="/api/register-view">¿No tienes cuenta? Regístrate aquí</a></label>
    <button id="login" class="btn btn-success w-100 py-2 mt-2" type="submit">Iniciar sesión</button>
  </form>
</main>
<c:if test="${not empty param.message and param.result == false}">
  <div class="container mt-4">
    <div class="alert alert-danger" role="alert">
      <c:out value="${param.message}"/>
    </div>
  </div>
</c:if>
<jsp:include page="${pageContext.request.contextPath}/layouts/footer.jsp"/>
<script>
  window.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("login-form");
    const btn = document.getElementById("login");
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
        btn.innerHTML = 'INICIAR SESIÓN';
      }
      form.classList.add("was-validated");
    }, false);

    const urlParams = new URLSearchParams(window.location.search);
    const result = urlParams.get('result');
    const mensaje = urlParams.get('message');
    if (result === 'true') {
      Swal.fire('¡Éxito!', mensaje, 'success');
    }
  }, false);
</script>
</body>
</html>