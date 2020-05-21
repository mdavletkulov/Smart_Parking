<#import "../../parts/common.ftlh" as c>

<@c.page>
    <h5>Кафедра</h5>
    <form action="${subdivision}" method="post" class="mt-2">
        <div class="form-group col-4 division">
            <label class="parking" for="division">Институты</label>
            <select class="form-control parking" name="division">
                <option selected="selected" value=""/>
                <#list divisions as division>
                    <option>${division.getName()}</option>
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
                   value="<#if place??>${subdivision.name}</#if>"
                   placeholder="Введите название кафедры"
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