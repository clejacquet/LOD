<css>
<#list cssLinks as link>
    ${link}
</#list>
</css>
<js>
<#list jsLinks as link>
    ${link}
</#list>
</js>
<header>
<#list headers as header>
    ${header}
</#list>
</header>
<content>
<#include "${content}">
</content>