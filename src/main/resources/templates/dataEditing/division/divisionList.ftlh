<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <div><h5 class="mb-3">Институты</h5>
    </div>
    <table class="table table-striped text-center table-hover table-dark" id="divisions" style="width:50%">
        <thead>
        <tr>
            <th class="align-middle" scope="col">Институт</th>
            <th></th>
            <th scope="col">
                <a href="/dataEdit/division/add" class="btn btn-primary stretched-link">
                    Добавить новый институт</a></th>
        </tr>
        </thead>
        <tbody>
        <#list divisions as division>
            <tr>
                <th class="align-middle" scope="row">${division.getName()}</th>
                <td>
                    <button id="${division.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
                </td>
                <td><a href="/dataEdit/division/edit/${division.id}"
                       class="btn btn-primary stretched-link">Редактировать</a>
                </td>

            </tr>
        </#list>
        </tbody>
    </table>

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
                    Вы действительно хотите удалить институт?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var divisionId;
        jQuery(document).ready(function ($) {

            $('#divisions').DataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
                columnDefs: [
                    {width: '60%', targets: 0},
                    {width: '25%', "orderable": false, targets: 1},
                    {width: '15%', "orderable": false, targets: 2},
                ],
                language: {
                    url: '/static/localization/Russian.json'
                },
                fixedColumns: true
            });

            $(document).on('click', 'button.askDelete', function(e) {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                divisionId = $(this).attr('id');
            });
        });

        $('button#delete').click(function () {
            window.location.href = '/dataEdit/division/delete/' + divisionId;
            return false;
        });
    </script>

</@c.page>