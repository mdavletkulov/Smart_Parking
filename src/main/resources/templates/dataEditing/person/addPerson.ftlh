<#import "../../parts/common.ftlh" as c>

<@c.page>
    <form action="${person}" method="post" class="mt-2">
        <div class="form-group">
            <label for="firstName">Имя</label>
            <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')} col-sm-4"
                   id="firstName"
                   value="<#if person??>${person.firstName}</#if>"
                   placeholder="Введите имя водителя"
                   name="firstName">
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="secondName">Фамилия</label>
            <input type="text" class="form-control ${(secondNameError??)?string('is-invalid', '')} col-sm-4"
                   id="secondName"
                   value="<#if person??>${person.secondName}</#if>"
                   placeholder="Введите фамилию водителя"
                   name="secondName">
            <#if secondNameError??>
                <div class="invalid-feedback">
                    ${secondNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="middleName">Отчество</label>
            <input type="text" class="form-control ${(middleNameError??)?string('is-invalid', '')} col-sm-4"
                   id="middleName"
                   value="<#if person??>${person.middleName}</#if>"
                   placeholder="Введите отчество водителя"
                   name="middleName">
            <#if middleNameError??>
                <div class="invalid-feedback">
                    ${middleNameError}
                </div>
            </#if>
        </div>
        <div class="btn-group btn-group-toggle form-group" data-toggle="buttons">
            <label class="btn btn-secondary">
                <input type="radio" name="student" id="student" value="false"> Студент
                <input type="radio" name="employee" id="employee" value="true"> Сотрудник
            </label>
        </div>
        <#if studentError??>
            <div class="invalid-feedback mb-3" style="display: block">
                ${studentError}
            </div>
        </#if>
        <#if employeeError??>
            <div class="invalid-feedback mb-3" style="display: block">
                ${employeeError}
            </div>
        </#if>
        <div class="form-group" hidden="hidden">
            <label for="courseError">Курс</label>
            <input type="text" class="form-control ${(courseError??)?string('is-invalid', '')} col-sm-4"
                   id="courseError"
                   value="<#if person??>${person.course}</#if>"
                   placeholder="Введите курс водителя"
                   name="courseError">
            <#if groupNameError??>
                <div class="invalid-feedback">
                    ${groupNameError}
                </div>
            </#if>
        </div>
        <div class="form-group" hidden="hidden">
            <label for="groupName">Группа</label>
            <input type="text" class="form-control ${(groupNameError??)?string('is-invalid', '')} col-sm-4"
                   id="groupName"
                   value="<#if person??>${person.groupName}</#if>"
                   placeholder="Введите группу водителя"
                   name="groupName">
            <#if groupNameError??>
                <div class="invalid-feedback">
                    ${groupNameError}
                </div>
            </#if>
        </div>
        <div class="btn-group btn-group-toggle form-group" data-toggle="buttons" hidden="hidden">
            <label for="typeJobPosition">Должность</label>
                    <select class="form-control division" name="jobPosition">
                        <option selected="selected" value=""/>
                        <#list jobPositions as jobPosition>
                            <option>${jobPosition.getName()}</option>
                        </#list>
                    </select>
                </label>
            <#if jobPositionError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${jobPositionError}
                </div>
            </#if>
            </div>
        <div class="form-group col-4 division" hidden="hidden">
            <label class="division" for="division" >Институт</label>
            <select class="form-control division" name="division">
                <option selected="selected" value=""/>
                <#list divisions as division>
                    <option>${division.getName()}</option>
                </#list>
            </select>
        </div>
        <div class="form-group col-4 subdivision" hidden="hidden">
            <label class="subdivision" for="subdivision" >Кафедра</label>
            <select class="form-control subdivision" name="subdivision">
                <option selected="selected" value=""/>
            </select>
            <#if subdivisionError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${subdivisionError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="passNum">Номер пропуска</label>
            <input type="text" class="form-control ${(passNumError??)?string('is-invalid', '')} col-sm-4"
                   id="passNum"
                   value="<#if person??>${person.passNum}</#if>"
                   placeholder="Введите номер пропуска водителя"
                   name="passNum">
            <#if passNumError??>
                <div class="invalid-feedback">
                    ${passNumError}
                </div>
            </#if>
        </div>


        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Create</button>
    </form>
</@c.page>