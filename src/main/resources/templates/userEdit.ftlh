<#import "parts/common.ftlh" as c>

<@c.page>
User editor
    <form action="/user" method="post">
        <input type="text" value="${user.username}" name="username" />
        <input type="text" value="${user.firstName}" name="firstName" />
        <input type="text" value="${user.secondName}" name="secondName" />
        <input type="text" value="${user.middleName}" name="middleName" />
        <#list roles as role>
            <div>
                <label class="radio-inline">
                    <input type="radio" name="role" id="${role}"  value="${role}" checked="${(user.role == role)?string("checked", "")}"/>${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Save</button>
    </form>
</@c.page>