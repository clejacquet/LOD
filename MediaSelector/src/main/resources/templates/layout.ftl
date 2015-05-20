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
    ${link}
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
                    <#if header_items[key].type == "item">
                <li><a href="${key}">${header_items[key].title}</a></li>
                    <#elseif header_items[key].type == "dropdown">
                <li class="dropdown">
                    <a href="${key}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${header_items[key].title}<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                    <#list header_items[key].subItems?keys as sub_key>
                        <li><a href="${header_items[key].subItems[sub_key]}">${sub_key}</a></li>
                    </#list>
                    </ul>
                </li>
                    </#if>
                </#list>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if user ??>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${user.login}<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="/user">Profil</a></li>
                            <li><a href="/user/logout">Log Out</a></li>
                        </ul>
                    </li>
                <#else>
                    <li><a href="/user/register">Register</a></li>
                    <li><a href="/user/connect">Log in</a></li>
                </#if>
            </ul>
        </div>
    </div>
</nav>

<div id="content">
<#include "${content}">
</div>

<hr style="visibility: hidden;"/>

<footer class="footer">
    <div class="container">
        <p class="text-muted">Media Selector - 2015 - (Clément JACQUET)</p>
    </div>
</footer>

<#list js_links as link>
${link}
</#list>

</body>
</html>