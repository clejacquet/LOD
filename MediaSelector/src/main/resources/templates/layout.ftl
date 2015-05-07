<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${title}</title>
    <#list css_links as link>
        <link href="/${artifact_name}${link}" rel="stylesheet">
    </#list>
</head>

<body>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${home}">Media Selector</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <#list header_items?keys as key>
                    <li><a href="${key}">${header_items[key]}</a></li>
                </#list>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if user ??>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${user.login}<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">Profil</a></li>
                            <li><a href="#">Log Out</a></li>
                        </ul>
                    </li>
                <#else>
                    <li><a href="/${artifact_name}/user/register">Register</a></li>
                    <li><a href="/${artifact_name}/user/connect">Log in</a></li>
                </#if>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
<#include "${content}">
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="/${artifact_name}/js/bootstrap.min.js"></script>
</body>
</html>