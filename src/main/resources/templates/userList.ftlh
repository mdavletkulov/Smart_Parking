<#import "parts/common.ftlh" as c>

<@c.page>
    <div>User List</div>
    <table>
        <thead>
        <tr>Name</tr>
        <tr>Role</tr>
        <th></th>
        </thead>
        <tbody>
        <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">edit</a></td>
        </tr>
        </#list>
        </tbody>
    </table>

</@c.page>