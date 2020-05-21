<#import "../../parts/common.ftlh" as c>

<@c.page>
    <div><h5 class="mb-3">Водители</h5>
    </div>
    <table id="persons" class="table table-striped no-border text-center"  style="width:100%" >
        <thead class="thead-dark">
        <tr>
            <th class="align-middle" scope="col">Водитель</th>
            <th class="align-middle" scope="col">Автомобиль</th>
            <th class="align-middle" scope="col">Номер</th>
            <th class="align-middle" scope="col">Статус</th>
            <th class="align-middle" scope="col">Должность</th>
            <th class="align-middle" scope="col">Институт</th>
            <th class="align-middle" scope="col">Группа</th>
            <th class="align-middle" scope="col">Курс</th>
            <th class="align-middle" scope="col">Пропуск</th>
            <th class="align-middle" scope="col">Дата истечения пропуска</th>
            <th class="align-middle" scope="col">Специальный статус</th>
            <th></th>
            <th class="align-middle" scope="col"><a href="/dataEdit/person/add"
                                                                         class="btn btn-primary stretched-link">
                    Добавить водителя</a></th>

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
                <td class="align-middle"><#if person.getJobPosition()??>${person.getJobPosition().getNamePosition()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getSubdivision()??>${person.getSubdivision().getDivision().getName()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getGroupName()??>${person.getGroupName()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getCourse()??>${person.getCourse()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getPassNum()??>${person.getPassNum()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getPassEndDate()??>${person.getPassEndDate()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.isSpecialStatus()>&#9745; <#else>&#x2716;</#if></td>
                <td class="align-middle">
                    <button id="${person.getId()}" type="button" class="btn btn-primary askDelete" data-toggle="modal">
                        Удалить
                    </button>
                </td>
                <td class="align-middle"><a href="/dataEdit/person/edit/${person.id}"
                                        class="btn btn-primary stretched-link">Редактировать</a>
                </td>

            </tr>
        </#list>
        </tbody>
    </table>
    </div>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Подтвердите удаление</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Вы действительно хотите удалить водителя?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>

    <script>
        var personId;
        jQuery(document).ready(function ($) {

            $('#persons').DataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
                columnDefs: [
                    {width: '15%', targets: 0},
                    {width: '10%', targets: 1},
                    {width: '10%', targets: 2},
                    {width: '5%', targets: 3},
                    {width: '5%', targets: 4},
                    {width: '5%', targets: 5},
                    {width: '5%', targets: 6},
                    {width: '5%', targets: 7},
                    {width: '5%', targets: 8},
                    {width: '10%', targets: 9},
                    {width: '10%', targets: 10},
                    {width: '5%', "orderable": false, targets: 11},
                    {width: '10%', "orderable": false, targets: 12},
                ],
                "bAutoWidth": false,
                language: {
                    url: '/static/localization/Russian.json'
                },
            });

            $('button.askDelete').click(function () {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                personId = $(this).attr('id');
            });
        });

        $('button#delete').click(function () {
            window.location.href = '/dataEdit/person/delete/' + personId;
            return false;
        });
    </script>

</@c.page>