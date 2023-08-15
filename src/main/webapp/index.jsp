<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Inicio de sesión</title>
  <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Indexstyle.css">
</head>
<body id="index">
<div class="container">
  <div class="row " style="margin-top: 80px;">
    <main class="form-signin w-100 m-auto">
      <form id="login-form" action="/login" class="needs-validation" novalidate method="post">
        <div class="col text-center">
          <svg xmlns="http://www.w3.org/2000/svg" width="72" height="67" fill="currentColor" class="bi bi-buildings-fill" viewBox="0 0 16 16">
            <path d="M15 .5a.5.5 0 0 0-.724-.447l-8 4A.5.5 0 0 0 6 4.5v3.14L.342 9.526A.5.5 0 0 0 0 10v5.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V14h1v1.5a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5V.5ZM2 11h1v1H2v-1Zm2 0h1v1H4v-1Zm-1 2v1H2v-1h1Zm1 0h1v1H4v-1Zm9-10v1h-1V3h1ZM8 5h1v1H8V5Zm1 2v1H8V7h1ZM8 9h1v1H8V9Zm2 0h1v1h-1V9Zm-1 2v1H8v-1h1Zm1 0h1v1h-1v-1Zm3-2v1h-1V9h1Zm-1 2h1v1h-1v-1Zm-2-4h1v1h-1V7Zm3 0v1h-1V7h1Zm-2-2v1h-1V5h1Zm1 0h1v1h-1V5Z"/>
          </svg>
        </div> <br>
        <h1 class="h3 mb-3 fw-normal text-center" id="title">SIBA Login</h1>
        <div class="form-floating">
          <input type="email" class="form-control" id="floatingInput" name="correo" pattern=".+@utez\.edu\.mx$" placeholder="name@example.com" size="35" required>
          <label for="floatingInput">Correo electrónico</label>
          <div id="feedbacke" class="invalid-feedback text-start" style="font-size: medium;">Ingresa un correo institucional válido</div>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPassword" name="contrasenia" placeholder="Contraseña" required>
          <label for="floatingPassword">Contraseña</label>
          <div id="feedbackp" class="invalid-feedback text-start" style="font-size: medium;">Ingresa tu contraseña</div>
        </div>
        <button id="login" class="btn btn-success w-100 py-2" type="submit">Iniciar sesión</button>
        <p class="mt-4 mb-3 text-body-secondary text-center">SIBA | 3°D Desarrollo de Software</p>
      </form>
    </main>
  </div>
</div>
<jsp:include page="./layouts/footer.jsp"/>

<script>
  window.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("login-form");
    const btn = document.getElementById("login");
    const feedbacke = document.getElementById("feedbacke");
    const feedbackp = document.getElementById("feedbackp");
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

    const result = ${param['result'] != null ? param['result'] : true};
    if (!result) {
      feedbacke.innerHTML = '${param['message']}';
      feedbackp.innerHTML = '';
      form.classList.add("was-validated");
    }
  }, false);
</script>
</body>
</html>