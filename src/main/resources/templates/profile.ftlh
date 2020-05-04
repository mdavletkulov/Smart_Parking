<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>${username}</h5>
    <#if message?has_content>
        ${message}
    </#if>
    <form method="post" class="mt-2">
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control col-sm-3" id="password" placeholder="Введите пароль"
                   name="password">
        </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control col-sm-3" id="email" placeholder="Введите email"
                       name="email" value="${email}">
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>