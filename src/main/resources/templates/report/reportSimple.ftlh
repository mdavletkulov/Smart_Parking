<#import "../parts/reportNav.ftlh" as rN>
<#import "../parts/dateReport.ftlh" as r>
<@rN.reportNav>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-5" role="alert">
            ${message}
        </div>
    </#if>
    <form method="post" action="/report/simple">
        <@r.dateReport/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if personId??><input type="hidden" name="personId" value="${personId}"/></#if>
        <button type="submit" class="btn btn-primary mt-3 send">Генерация отчета</button>
    </form>
</@rN.reportNav>