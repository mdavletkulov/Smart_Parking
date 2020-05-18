<#import "parts/common.ftlh" as c>
<#import "parts/dateReport.ftlh" as r>

<@c.page>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-5" role="alert">
            ${message}
        </div>
    </#if>
    <form action="report" method="post" class="mt-2">
        <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" class="form-control col-sm-4"
                   id="name"
                   value="AAA"
                   readonly = "readonly"
                   name="name">
        </div>
        <@r.dateReport/>
    </form>
    </div>
    </div>

</@c.page>