<!DOCTYPE html>
<html>
<head>
    <title>LOGS</title>
</head>
<body>
<div>
    <h1><a href="/logs">refresh</a></h1>
</div>
<div>
    <h1><a href="/index">index</a></h1>
</div>
<ul>
    <#list records as rec>
        <#if rec.type() == "INFO">
            <#assign typeColor="green">
        <#elseif rec.type() == "ERROR">
            <#assign typeColor="red">
        </#if>

        <li style="color: ${typeColor}">${rec.id()} - ${rec.type()} - ${rec.date()} - <b>${rec.pref()}</b> - ${rec.msg()}</li>
    </#list>
</ul>
</body>
</html>