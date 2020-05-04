<#macro login path isRegisterForm>
    <form action="${path}" method="post" class="mt-2">
        <div class="form-group">
            <label for="username">User Name:</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')} col-sm-3" id="username"
                   value="<#if user??>${user.username}</#if>"
                   placeholder="Введите имя пользователя"
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
        <#if isRegisterForm>
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
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')} col-sm-3"
                       id="email" placeholder="Введите email"
                       value="<#if user??>${user.email}</#if>"
                       name="email">
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
            <div class="col-sm-6">
            <div class="g-recaptcha" data-sitekey="6Ld2WPIUAAAAAD7PuG0wfhYFEIK408dIjng1u1fa"></div>
            <#if captchaError??>
                <div class="alert alert-danger" role="alert">
                    ${captchaError}
                </div>
                </div>
        </#if>
    </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign in</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Выйти</button>
    </form>
</#macro>