<html>
<head><title>Catalog Report</title></head>
<body>
    <h1>Resursele din Catalog</h1>
    <ul>
        <#list resources as res>
            <li><b>${res.title}</b> de ${res.author} (Anul: ${res.year?c}) - Locatie: <a href="${res.location}">${res.location}</a></li>
        </#list>
    </ul>
</body>
</html>