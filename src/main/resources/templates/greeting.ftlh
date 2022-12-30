<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <#if isAuth>
        <h5>Hello, ${name}!</h5>
        <div>Это упрощённый клон сайта Twitter</div>
        <#else>
            <h5>Hello, guest!</h5>
            <div>Это упрощённый клон сайта Twitter</div>
    </#if>
</@c.page>