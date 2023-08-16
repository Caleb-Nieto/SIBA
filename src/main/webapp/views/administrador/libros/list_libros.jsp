<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Libros</title>
    <jsp:include page="${pageContext.request.contextPath}/layouts/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainStyle.css">
    <jsp:include page="${pageContext.request.contextPath}/layouts/navbar.jsp"/>
</head>
<body>
<div class="container">
    <div class="row " style="margin-top: 100px;">
        <div class="col-12 mb-12 mt-12 ">
            <div class="row">
                <div class="container-fluid mb-4" style="background: #045c4a; color: white;">
                    <h3 class="text-white text-center">Libros</h3>
                </div>
                <div class="col-10">
                    <form action="/api/libro/search" method="get">
                        <div class="row">
                            <div class="col">
                                <input type="search" class="form-control" name="palabra" id="palabra" placeholder="Nombre del libbro" maxlength="30">
                            </div>
                            <div class="col">
                                <button type="submit"  class="btn btn-secondary">Buscar</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col">
                    <a href="/api/libro/libro-view-save" class="btn btn-success">Agregar Libro</a>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${empty libros}">
        <div class="alert alert-success d-flex align-items-center mt-4" role="alert">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
            </svg>
            <div>
                &nbsp; &nbsp; Aún no hay registros
            </div>
        </div>
    </c:if>
    <div class="row justify-content-center" style="margin-top: 50px";>
        <div class="col-10 ">
            <div class="row row-cols-1 row-cols-md-2 g-4">
                <c:forEach items="${libros}" var="libro">
                    <div class="col">
                        <div class="card mb-3 text-center" style="width: 500px; height: 350px;">
                            <div class="row g-0">
                                <div class="col-md-5">
                                    <img src="/api/libro/loadfile?file=${libro.id}" class="img-fluid rounded-start" style="height: 350px;" alt="${libro.titulo}">
                                </div>
                                <div class="col-md-7">
                                    <div class="card-body">
                                        <h3 class="card-title"><c:out value="${libro.titulo}"/></h3>
                                        <h5 class="card-text"><c:out value="${libro.ejemplares}"/>
                                            <c:choose>
                                                <c:when test="${libro.ejemplares > 1}">
                                                    Ejemplares
                                                </c:when>
                                                <c:otherwise>
                                                    Ejemplar
                                                </c:otherwise>
                                            </c:choose>
                                        </h5>
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
                                        <p class="card-text">
                                            <small class="text-body-secondary">
                                                <c:out value="Pasillo ${libro.ubicacion.pasillo}, seccion ${libro.ubicacion.seccion}, estante ${libro.ubicacion.estante}"/>
                                            </small>
                                        </p>
                                        <div class="row">
                                            <form method="get" action="/api/ejemplar/ejemplar-view-save">
                                                <input hidden value="${libro.id}" name="id_libro"/>
                                                <div class="d-grid gap-1 mx-auto">
                                                    <button type="submit" class="btn btn-success btn-sm">
                                                        Agregar ejemplar
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                            <div class="row mt-2">
                                                <c:if test="${libro.ejemplares == 1}">
                                                    <form method="get" action="/api/libro/libro-view-update">
                                                        <input hidden value="${libro.id}" name="id_libro"/>
                                                        <div class="d-grid gap-1 mx-auto">
                                                            <button type="submit" class="btn btn-primary btn-sm">
                                                                Editar libro
                                                            </button>
                                                        </div>
                                                    </form>
                                                </c:if>
                                            </div>
                                        <div class="row mt-2">
                                            <c:if test="${libro.ejemplares > 1}">
                                                <form method="get" action="/api/ejemplar/ejemplares">
                                                    <input hidden value="${libro.id}" name="id_libro"/>
                                                    <div class="d-grid gap-1 mx-auto">
                                                        <button type="submit" class="btn btn-secondary btn-sm">
                                                            Ver ejemplares
                                                        </button>
                                                    </div>
                                                </form>
                                            </c:if>
                                            <c:if test="${libro.ejemplares == 1}">
                                                <form class="deleteForm" method="post" action="/api/libro/delete">
                                                    <input hidden value="${libro.id}" name="id_libro"/>
                                                    <div class="d-grid gap-1 mx-auto">
                                                        <button type="button" onclick="alerta(this)" class="btn btn-outline-danger btn-sm">
                                                            Eliminar
                                                        </button>
                                                    </div>
                                                </form>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${not empty libros and totalPaginas > 1}">
                <div class="pagination d-flex mt-2 mb-2">
                    <div class="btn-group" role="group" aria-label="Botones de paginación">
                        <c:if test="${paginaActual > 1}">
                            <a href="/api/libro/libros?page=${paginaActual - 1}" class="btn btn-light border-black"><< Anterior</a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPaginas}" var="numeroPagina">
                            <c:choose>
                                <c:when test="${numeroPagina == paginaActual}">
                                    <a href="/api/libro/libros?page=${numeroPagina}" class="btn btn-light active border-black">${numeroPagina}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/api/libro/libros?page=${numeroPagina}" class="btn btn-light border-black">${numeroPagina}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${paginaActual < totalPaginas}">
                            <a href="/api/libro/libros?page=${paginaActual + 1}" class="btn btn-light border-black">Siguiente >></a>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
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