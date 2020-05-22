<#import "../../parts/common.ftlh" as c>

<@c.page>
    <div>
        <a href="/dataEdit/division"
           class="btn btn-primary stretched-link">Назад</a>
    </div>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <h5>Институт</h5>
    <form action="/dataEdit/division/add" method="post" class="mt-2">
        <div class="form-group">
            <label for="name">Название института</label>
            <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')} col-sm-4"
                   id="name"
                   value="<#if place??>${subdivision.name}</#if>"
                   placeholder="Введите название института"
                   name="name">
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
</@c.page>