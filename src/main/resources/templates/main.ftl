<!DOCTYPE html>
<html>
<head>
    <title>Example</title>
</head>
<body>
<div>
    <h1><a href="/backup">backup</a></h1>
</div>
<div>
    <h1><a href="/index">refresh</a></h1>
</div>
<div>
    <h1><a href="/logs">logs</a></h1>
</div>
<div>
    <form method="post" action="/ssh/key/add">
        <label>
            path to ssh key
            <input name="path" type="text" placeholder="${sshKeyPath!"add path to your ssh key"}">
        </label>
        <input type="submit" value="save">
    </form>
</div>
<br>
<div>
    <form method="post" action="/folder/add">
        <label>
            pref
            <input name="pref" type="text">
        </label>
        <label>
            path
            <input name="path" type="text">
        </label>
        <label>
            remote
            <input name="remote" type="text">
        </label>
        <input type="submit" value="save">
    </form>
</div>

<ul>
    <#list folders as folder>
        <li>${folder.id} - ${folder.pref} - ${folder.path} - ${folder.remote} | <a href="/folder/${folder.id}/delete" style="color: red">X</a></li>
    </#list>
</ul>
</body>
</html>