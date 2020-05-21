<#import "../../parts/common.ftlh" as c>

<@c.page>
    <h5>Парковка</h5>
    <form action="${parking}" method="post" class="mt-2">
        <div class="form-group">
            <label for="description">Название парковки</label>
            <input type="text" class="form-control ${(descriptionError??)?string('is-invalid', '')} col-sm-4"
                   id="description"
                   value="<#if place??>${parking.description}</#if>"
                   placeholder="Введите название парковки"
                   name="description">
            <#if descriptionError??>
                <div class="invalid-feedback">
                    ${descriptionError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
</@c.page>