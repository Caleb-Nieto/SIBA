
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Ejemplares</title>
  <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
  <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
</head>
<body>
<div class="container">
  <div class="row" style="margin-top: 100px;">
    <div class="container-fluid mb-4" style="background: #045c4a; color: white;">
      <h3 class="text-white text-center">
        Ejemplares de: <c:out value="${ejemplares[0].libro.titulo}"/>
        <br/>
        ISBN: <c:out value="${ejemplares[0].libro.isbn}"/>
      </h3>
    </div>
    <div class="col-12 mb-12 mt-12 mb-4">
      <div class="row">
        <div class="col">
          <form action="/api/ejemplar/ejemplar-view-save" method="get">
            <input hidden value="${ejemplares[0].libro.titulo}" name="titulo">
            <input hidden value="${ejemplares[0].libro.id}" name="id_libro">
            <button type="submit" class="btn btn-success">Agregar Ejemplar</button>
          </form>
        </div>
      </div>
    </div>
  </div>
  <br>
  <div class="row justify-content-center">
    <table class="table bg-white text-center">
      <thead>
      <tr class="table-success">
        <th scope="col">#</th>
        <th scope="col">Identificador de ejemplar</th>
        <th scope="col">Observaciones</th>
        <th scope="col">Opciones</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${ejemplares}" var="ejemplar" varStatus="s">
        <tr>
          <td><c:out value="${s.count}"/></td>
          <td><c:out value="${ejemplar.ejemplar}"/></td>
          <td><c:out value="${ejemplar.observaciones}"/></td>
          <td class="d-grid d-md-flex justify-content-md-center">
              <form method="get" action="/api/ejemplar/ejemplar-view-update">
                <input hidden value="${ejemplar.id_ejemplar}" name="id_ejemplar"/>
                <input hidden value="${ejemplares[0].libro.id}" name="id_libro">
                <button type="submit" class="btn btn-primary btn-sm">
                  Editar
                </button>
              </form>
              &nbsp
            <c:if test="${s.count != 1}">
              <form class="deleteForm" method="post" action="/api/ejemplar/delete">
                <input hidden value="${ejemplar.id_ejemplar}" name="id_ejemplar"/>
                <button type="button" onclick="alerta(this)" class="btn btn-outline-danger btn-sm">
                  Eliminar
                </button>
              </form>
            </c:if>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <c:if test="${empty ejemplares}">
      <div class="alert alert-success d-flex align-items-center" role="alert">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
          <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </svg>
        <div>
          &nbsp;Aún no hay registros
        </div>
      </div>
    </c:if>
  </div>
  <c:if test="${not empty ejemplares and totalPaginas > 1}">
    <div class="pagination d-flex mt-2 mb-2">
      <div class="btn-group" role="group" aria-label="Botones de paginación">
        <c:if test="${paginaActual > 1}">
          <a href="/api/autor/autores?page=${paginaActual - 1}" class="btn btn-light border-black"><< Anterior</a>
        </c:if>

        <c:forEach begin="1" end="${totalPaginas}" var="numeroPagina">
          <c:choose>
            <c:when test="${numeroPagina == paginaActual}">
              <a href="/api/ejemplar/ejemplares?page=${numeroPagina}" class="btn btn-light active border-black">${numeroPagina}</a>
            </c:when>
            <c:otherwise>
              <a href="/api/ejemplar/ejemplares?page=${numeroPagina}" class="btn btn-light border-black">${numeroPagina}</a>
            </c:otherwise>
          </c:choose>
        </c:forEach>

        <c:if test="${paginaActual < totalPaginas}">
          <a href="/api/ejemplar/ejemplares?page=${paginaActual + 1}" class="btn btn-light border-black">Siguiente >></a>
        </c:if>
      </div>
    </div>
  </c:if>
</div>
<jsp:include page="${pageContext.request.contextPath}/layouts/footer.jsp"/>
<script>
  //Codigo que se debe utilizar cuando quieres que el usuario tenga que confirmar la accion que quiere realizar(ej.cuando presiona un boton de eliminar)

  let enviarFormulario = false;

  //Funcio que recibe un boton como parametro y despliega un mensaje de alerta, antes de eliminar
  function alerta(button){
    Swal.fire({
      title: '¿Eliminar?',
      text: "¡No podrás revertir los cambios!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        enviarFormulario = true;
        let form = button.parentElement;
        form.submit();
      }
    })
  }
  //-----------------------------------------------

  //Selecciona cada formulario por el nombre de la clase y mientras el formulario sea false, este no se enviara
  document.querySelectorAll('.deleteForm').forEach(form =>{
    form.addEventListener('submit', event =>{
      if(!enviarFormulario){
        event.preventDefault();
      }
    })
  })
  //--------------------------------------------------

  /*Mensaje de la libreria sweetalert que se despliega cuando en la url el parametro message tiene un valor.
      Despues, en una variable se recoge el mensaje y lo coloca como mensaje en la alerta
  */
  document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const result = urlParams.get('result');
    const mensaje = urlParams.get('message');
    if (result === 'true') {
      Swal.fire('¡Éxito!', mensaje, 'success');
    }else{
      Swal.fire('¡Error!', mensaje, 'error');
    }
  });
</script>
</body>
</html>