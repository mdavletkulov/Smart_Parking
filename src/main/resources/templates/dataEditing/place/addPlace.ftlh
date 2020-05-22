<#import "../../parts/common.ftlh" as c>

<@c.page>
    <div>
        <a href="/dataEdit/place"
           class="btn btn-primary stretched-link">Назад</a>
    </div>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <h5>Парковочное место</h5>
    <form action="/dataEdit/place/add" method="post" class="mt-2">
        <div class="form-group col-4 parking">
            <label class="parking" for="parking">Парковки</label>
            <select class="form-control parking" name="parking">
                <#list parkings as parking>
                    <option value="${parking.getId()}">${parking.getDescription()}</option>
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
                   value="<#if place?? && place.getPlaceNumber()??>${place.placeNumber}</#if>"
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