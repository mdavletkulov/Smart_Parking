<#import "../../parts/common.ftlh" as c>

<@c.page>
    <script type="text/javascript" src="/static/js/tableSearch.js"></script>
    <div><h5 class="mb-3">Институты</h5>
    </div>

    <div class="form-group" style="margin-bottom: 5%">
        <input type="text" class="form-control pull-right" id="search" placeholder="Поиск по таблице">
    </div>
    <table class="table table-striped text-center table-hover col-5" id="persons">
        <thead class="thead-dark">
        <tr class="row">
            <th class="align-middle col-sm-6" scope="col">Институт</th>
            <th class="align-middle col-sm-6" class="align-middle" colspan="2" scope="col">
                <a href="/dataEdit/division/add" class="btn btn-primary stretched-link">
                    Добавить новый институт</a></th>
        </tr>
        </thead>
        <tbody>
        <#list divisions as division>
            <tr class="row">
                <th class="align-middle col-sm-6" scope="row">${division.getName()}</th>
                <td class="col-sm-4"><a href="/dataEdit/division/edit/${division.id}"
                                        class="btn btn-primary stretched-link">Редактировать</a>
                </td>
                <td class="col-sm-2">
                    <button id="${division.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
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
        var userId;
        jQuery(document).ready(function ($) {
            $('button.askDelete').click(function () {
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