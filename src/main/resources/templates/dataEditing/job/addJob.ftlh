<#import "../../parts/common.ftlh" as c>

<@c.page>
    <h5>Должность</h5>
    <form action="/dataEdit/job/add" method="post" class="mt-2">

        <div class="form-group col-4 typeJobPosition">
            <label class="typeJobPosition" for="typeJobPosition">Подразделение</label>
            <select class="form-control typeJobPosition" name="typeJobPosition">
                <#list typeJobPositions as typeJobPosition>
                    <option>${typeJobPosition}</option>
                </#list>
            </select>
            <#if typeJobPositionsError??>
                <div class="invalid-feedback">
                    ${typeJobPositionError}
                </div>
            </#if>
        </div>

        <div class="form-group">
            <label for="namePosition">Название должности</label>
            <input type="text" class="form-control ${(namePositionError??)?string('is-invalid', '')} col-sm-4"
                   id="namePosition"
                   value="<#if place??>${parking.description}</#if>"
                   placeholder="Введите Название должности"
                   name="namePosition">
            <#if namePositionError??>
                <div class="invalid-feedback">
                    ${namePositionError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="division"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
</@c.page>