<#import "../../parts/common.ftlh" as c>

<@c.page>
    <div>
        <a href="/dataEdit/subdivision"
           class="btn btn-primary stretched-link">Назад</a>
    </div>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <h5>Кафедра</h5>
    <form action="/dataEdit/subdivision/edit/${subdivision.getId()}" method="post" class="mt-2">
        <div class="form-group col-4 division">
            <label class="division" for="division">Институты</label>
            <select id="division" class="form-control division" name="division" >
                <#list divisions as division>
                    <option <#if subdivision.getDivision() == division> selected="selected" </#if>value="${division.getId()}">${division.getName()}</option>
                </#list>
            </select>
            <#if divisionError??>
                <div class="invalid-feedback">
                    ${divisionError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="name">Название кафедры</label>
            <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')} col-sm-4"
                   id="name"
                   value="${subdivision.name}"
                   placeholder="Введите название кафедры"
                   name="name">
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить</button>
    </form>

    <script>
    </script>
</@c.page>