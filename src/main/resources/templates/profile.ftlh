<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>${user.username}</h5>
    <#if message?has_content>
        ${message}
    </#if>
    <form method="post" class="mt-2">
        <div class="form-group">
            <label for="email">Имя:</label>
            <input type="text" class="form-control col-sm-3" id="firstName" placeholder="Введите Имя"
                   name="firstName" value="${user.firstName}">
        </div>
        <div class="form-group">
            <label for="email">Фамилия:</label>
            <input type="text" class="form-control col-sm-3" id="secondName" placeholder="Введите Фамилия"
                   name="secondName" value="${user.secondName}">
        </div>
        <div class="form-group">
            <label for="email">Отчество:</label>
            <input type="text" class="form-control col-sm-3" id="middleName" placeholder="Введите Отчество"
                   name="middleName" value="${user.middleName}">
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control col-sm-3" id="username" placeholder="Введите email"
                   name="username" value="${user.username}">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control col-sm-3" id="password" placeholder="Введите пароль"
                   name="password">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>