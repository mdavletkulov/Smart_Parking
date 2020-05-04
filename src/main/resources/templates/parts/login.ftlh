<#macro login>
    <form action="/login" method="post" class="mt-2">
        <div class="form-group">
            <label for="username">E-mail:</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')} col-sm-3" id="username"
                   value="<#if user??>${user.username}</#if>"
                   placeholder="Введите e-mail"
                   name="username">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')} col-sm-3"
                   id="password" placeholder="Введите пароль"
                   name="password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <a href="/registration">Добавить нового пользователя</a>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Выйти</button>
    </form>
</#macro>

<#macro registration>
    <form action="/registration" method="post" class="mt-2">
        <div class="form-group">
            <label for="firstName">Имя</label>
            <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')} col-sm-3"
                   id="firstName"
                   value="<#if user??>${user.firstName}</#if>"
                   placeholder="Введите имя пользователя"
                   name="firstName">
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="secondName">Фамилия</label>
            <input type="text" class="form-control ${(secondNameError??)?string('is-invalid', '')} col-sm-3"
                   id="secondName"
                   value="<#if user??>${user.secondName}</#if>"
                   placeholder="Введите фамилию"
                   name="secondName">
            <#if secondNameError??>
                <div class="invalid-feedback">
                    ${secondNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="middleName">Отчество</label>
            <input type="text" class="form-control col-sm-3" id="middleName"
                   value="<#if user??>${user.middleName}</#if>"
                   placeholder="Введите отчество"
                   name="middleName">
        </div>
        <div class="form-group">
            <label for="username">E-mail:</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')} col-sm-3" id="username"
                   value="<#if user??>${user.username}</#if>"
                   placeholder="Введите e-mail"
                   name="username">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')} col-sm-3"
                   id="password" placeholder="Введите пароль"
                   name="password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password2">Password Confirmation:</label>
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')} col-sm-3"
                   id="password2" placeholder="Повторите пароль"
                   name="password2">
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>


        <div class="btn-group btn-group-toggle" data-toggle="buttons">
            <#list roles as role>
                <label class="btn btn-secondary <#if user??> ${(user.role == role)?string("active", "")}                 </#if>">
                    <input type="radio" name="role" id="${role}" value="${role}"
                           checked="<#if user??> ${(user.role == role)?string("checked", "")}</#if>"> ${role}
                </label>
            </#list>
        </div>

        <div class="col-sm-1">
            <div class="g-recaptcha" data-sitekey="6Ld2WPIUAAAAAD7PuG0wfhYFEIK408dIjng1u1fa"></div>
            <#if captchaError??>
            <div class="alert alert-danger" role="alert">
                ${captchaError}
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Create</button>
    </form>
</#macro>