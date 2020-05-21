<#import "../../parts/common.ftlh" as c>

<@c.page>
    <script type="text/javascript" src="/static/js/tableSearch.js"></script>
    <h5>Автомобиль</h5>
    <form action="${automobile}" method="post" class="mt-2">
        <div class="form-group">
            <label for="number">Марка и модель автомобиля</label>
            <input type="text" class="form-control ${(numberError??)?string('is-invalid', '')} col-sm-4"
                   id="number"
                   value="<#if place??>${subdivision.name}</#if>"
                   placeholder="Введите марку и модель автомобиля"
                   name="number">
            <#if numberError??>
                <div class="invalid-feedback">
                    ${numberError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="model">Номер автомобиля</label>
            <input type="text" class="form-control ${(modelError??)?string('is-invalid', '')} col-sm-4"
                   id="model"
                   value="<#if place??>${subdivision.name}</#if>"
                   placeholder="Введите номер автомобиля"
                   name="model">
            <#if modelError??>
                <div class="invalid-feedback">
                    ${modelError}
                </div>
            </#if>
        </div>
        <div class="form-group col-4 division">
            <label class="parking" for="division">Цвет автомобиля</label>
            <select class="form-control parking" name="division">
                <option selected="selected" value=""/>
                <#list colors as color>
                    <option>${color.getName()}</option>
                </#list>
            </select>
            <#if colorError??>
                <div class="invalid-feedback">
                    ${colorError}
                </div>
            </#if>
        </div>

        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Показать водителей
        </button>
        <div class="collapse" id="collapseExample">
            <div class="card card-body">
        <div class="form-group" style="margin-bottom: 5%">
            <input type="text" class="form-control pull-right" id="search" placeholder="Поиск по таблице">
        </div>
        <table class="table table-striped text-center table-hover" id="persons">
            <thead class="thead-dark">
            <tr>
                <th class="align-middle" scope="col">Водитель</th>
                <th class="align-middle" scope="col">Автомобиль</th>
                <th class="align-middle" scope="col">Номер</th>
                <th class="align-middle" scope="col">Статус</th>
                <th class="align-middle" scope="col">Подразделение</th>
                <th class="align-middle" scope="col">Должность</th>
                <th class="align-middle" scope="col">Институт</th>
                <th class="align-middle" scope="col">Группа</th>
                <th class="align-middle" scope="col">Курс</th>
                <th class="align-middle" scope="col">Пропуск</th>
                <th class="align-middle" scope="col">Дата истечения пропуска</th>
                <th class="align-middle" scope="col">Специальный статус</th>
                <th class="align-middle" class="align-middle" scope="col"></th>

            </tr>
            </thead>
            <tbody>
            <#list persons as person>
                <tr>
                    <th class="align-middle" scope="row">${person.getFullName()}</th>
                    <#list automobiles as auto>
                        <#if auto.getPerson().getId() == person.getId()>
                            <td class="align-middle">${auto.getModel()} </td></#if>
                        <#if auto.getPerson().getId() == person.getId()>
                            <td class="align-middle">${auto.getNumber()} </td></#if>
                    </#list>
                    <td class="align-middle"><#if person.isStudent()>Студент</#if> <#if person.isEmployee()>Сотрудник</#if></td>
                    <td class="align-middle"><#if person.getJobPosition()??>${person.getJobPosition().getTypeJobPosition()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.getJobPosition()??>${person.getJobPosition().getNamePosition()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.getSubdivision()??>${person.getSubdivision().getDivision().getName()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.getGroupName()??>${person.getGroupName()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.getCourse()??>${person.getCourse()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.getPassNum()??>${person.getPassNum()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.getPassEndDate()??>${person.getPassEndDate()}<#else>&#x2716;</#if></td>
                    <td class="align-middle"><#if person.isSpecialStatus()>&#9745; <#else>&#x2716;</#if></td>
                    <td class="col-sm-3">
                        <button id="${person.getId()}" type="button" class="btn btn-primary choose"
                                data-toggle="modal">
                            Выбрать водителя
                        </button>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
            </div>
        </div>

        <input type="hidden" id="person" name="person"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>

    <script>
        var personId;
        jQuery(document).ready(function ($) {
            $('#persons').DataTable({
                "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
            });

            $('button.choose').click(function () {
                jQuery.noConflict();
                personId = $(this).attr('id');
                $('input#person').val(personId);
            });
        });


    </script>
</@c.page>