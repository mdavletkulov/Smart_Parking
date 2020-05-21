<#import "../../parts/common.ftlh" as c>

<@c.page>
    <h5>Парковочное место</h5>
    <form action="${place}" method="post" class="mt-2">
        <div class="form-group col-4 parking">
            <label class="parking" for="parking">Парковки</label>
            <select class="form-control parking" name="parking">
                <option selected="selected" value=""/>
                <#list parkings as parking>
                    <option>${parking.getDescription()}</option>
                </#list>
            </select>
            <#if parkingError??>
                <div class="invalid-feedback">
                    ${parkingError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="placeNumber">Номер места</label>
            <input type="text" class="form-control ${(placeNumberError??)?string('is-invalid', '')} col-sm-4"
                   id="placeNumber"
                   value="<#if place??>${place.placeNumber}</#if>"
                   placeholder="Введите номер парковочного места"
                   name="placeNumber">
            <#if placeNumberError??>
                <div class="invalid-feedback">
                    ${placeNumberError}
                </div>
            </#if>
        </div>
        <div class="form-check">
            <input name="specialStatus" type="checkbox"
                   class="form-check-input ${(specialStatusError??)?string('is-invalid', '')}" id="specialStatus">
            <label class="form-check-label" for="exampleCheck1">Специальный статус</label>
            <#if specialStatusError??>
                <div class="invalid-feedback">
                    ${specialStatusError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
</@c.page>