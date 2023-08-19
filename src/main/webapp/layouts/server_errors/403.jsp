<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ERROR 403</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}css/errors.css" />
</head>
<body>
<div class="error-page">
    <div>
        <h1 data-h1="403">403</h1>
        <p data-p="FORBIDDEN">FORBIDDEN</p>
    </div>
</div>

<a href="${pageContext.request.contextPath}/api/login" class="back">Regresar</a>

<div id="tsparticles"></div>

<script type="text/javascript"
        src="https://cdn.jsdelivr.net/npm/tsparticles@2.3.4/tsparticles.bundle.min.js"></script>
<script type="text/javascript" src="js/errors.js"></script>
</body>
</html>
