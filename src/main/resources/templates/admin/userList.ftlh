<#import "../parts/common.ftlh" as c>

<@c.page>
    <div>User List</div>
    <table>
        <thead>
        <tr>E-mail</tr>
        <tr>Ф.И.О</tr>
        <tr>Роль</tr>
        <th></th>
        </thead>
        <tbody>
        <#list users as user>
<#--            <#if user.username != currentUser.username>-->
        <tr>
            <td>${user.username}</td>
            <td>${user.getFullName()}</td>
            <td>${user.getRole()}</td>
            <td><a href="/user/${user.id}">edit</a></td>
        </tr>
<#--            </#if>-->
        </#list>
        </tbody>
    </table>

</@c.page>